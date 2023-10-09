!function ($) {
    "use strict";
    $.fn.lifestream = function (config) {
        return this.each(function () {
            var outputElement = $(this),
                settings = jQuery.extend({classname: "lifestream", feedloaded: null, limit: 10, list: []}, config),
                data = {count: settings.list.length, items: []}, itemsettings = jQuery.extend(!0, {}, settings),
                finished = function (inputdata) {
                    $.merge(data.items, inputdata), data.items.sort(function (a, b) {
                        return b.date - a.date
                    });
                    for (var item, items = data.items, length = items.length < settings.limit ? items.length : settings.limit, i = 0, ul = $('<ul class="' + settings.classname + '"/>'); length > i; i++) item = items[i], item.html && $('<li class="' + settings.classname + "-" + item.config.service + '">').data("name", item.config.service).data("url", item.url || "#").data("time", item.date).append(item.html).appendTo(ul);
                    outputElement.html(ul), $.isFunction(settings.feedloaded) && settings.feedloaded()
                }, load = function () {
                    var i = 0, j = settings.list.length;
                    for (delete itemsettings.list; j > i; i++) {
                        var config = settings.list[i];
                        $.fn.lifestream.feeds[config.service] && $.isFunction($.fn.lifestream.feeds[config.service]) && config.user && (config._settings = itemsettings, $.fn.lifestream.feeds[config.service](config, finished))
                    }
                };
            jQuery.tmpl ? load() : jQuery.getScript("//ajax.aspnetcdn.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js", load)
        })
    }, $.fn.lifestream.createYqlUrl = function (query) {
        return (("https:" === document.location.protocol ? "https" : "http") + "://query.yahooapis.com/v1/public/yql?q=__QUERY__&env=store://datatables.org/alltableswithkeys&format=json").replace("__QUERY__", encodeURIComponent(query))
    }, $.fn.lifestream.feeds = $.fn.lifestream.feeds || {}, Object.keys || (Object.keys = function (o) {
        if (o !== Object(o)) throw new TypeError("Object.keys called on non-object");
        var p, ret = [];
        for (p in o) Object.prototype.hasOwnProperty.call(o, p) && ret.push(p);
        return ret
    })
}(jQuery), function ($) {
    $.fn.lifestream.feeds.bitbucket = function (config, callback) {
        var template = $.extend({}, {
                commit: '<a href="http://bitbucket.org/${owner}/${name}/changeset/${node}/">committed</a> at <a href="http://bitbucket.org/${owner}/${name}/">${owner}/${name}</a>',
                pullrequest_fulfilled: 'fulfilled a pull request at <a href="http://bitbucket.org/${owner}/${name}/">${owner}/${name}</a>',
                pullrequest_rejected: 'rejected a pull request at <a href="http://bitbucket.org/${owner}/${name}/">${owner}/${name}</a>',
                pullrequest_created: 'created a pull request at <a href="http://bitbucket.org/${owner}/${name}/">${owner}/${name}</a>',
                create: 'created a new project at <a href="http://bitbucket.org/${owner}/${name}/">${owner}/${name}</a>',
                fork: 'forked <a href="http://bitbucket.org/${owner}/${name}/">${owner}/${name}</a>'
            }, config.template),
            supported_events = ["commit", "pullrequest_fulfilled", "pullrequest_rejected", "pullrequest_created", "create", "fork"],
            parseBitbucketStatus = function (status) {
                return -1 !== $.inArray(status.event, supported_events) && status.repository ? "commit" === status.event ? $.tmpl(template.commit, {
                    owner: status.repository.owner,
                    name: status.repository.name,
                    node: status.node
                }) : $.tmpl(template[status.event], {
                    owner: status.repository.owner,
                    name: status.repository.name
                }) : void 0
            }, parseBitbucket = function (input) {
                var output = [];
                return input.query && input.query.count && input.query.count > 0 && $.each(input.query.results.json, function () {
                    output.push({
                        date: new Date(this.events.created_on.replace(/-/g, "/")),
                        config: config,
                        html: parseBitbucketStatus(this.events)
                    })
                }), output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select events.event,events.node, events.created_on,events.repository.name, events.repository.owner from json where url = "https://api.bitbucket.org/1.0/users/' + config.user + '/events/"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseBitbucket(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.bitly = function (config, callback) {
        var template = $.extend({}, {created: 'created URL <a href="${short_url}" title="${title}">${short_url}</a>'}, config.template);
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select data.short_url, data.created, data.title from json where url="http://bitly.com/u/' + config.user + '.json"'),
            dataType: "jsonp",
            success: function (input) {
                var j, list, output = [], i = 0;
                if (input.query && input.query.count && input.query.results.json) for (list = input.query.results.json, j = list.length; j > i; i++) {
                    var item = list[i].data;
                    output.push({
                        date: new Date(1e3 * item.created),
                        config: config,
                        html: $.tmpl(template.created, item)
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.blogger = function (config, callback) {
        var template = $.extend({}, {posted: 'posted <a href="${origLink}">${title}</a>'}, config.template),
            parseBlogger = function (input) {
                var list, j, item, k, l, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.feed.entry) for (list = input.query.results.feed.entry, j = list.length; j > i; i++) {
                    if (item = list[i], !item.origLink) for (k = 0, l = item.link.length; l > k; k++) "alternate" === item.link[k].rel && (item.origLink = item.link[k].href);
                    item.origLink && (item.title.content && (item.title = item.title.content), output.push({
                        date: new Date(item.published),
                        config: config,
                        html: $.tmpl(template.posted, item)
                    }))
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://' + config.user + '.blogspot.com/feeds/posts/default"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseBlogger(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.citeulike = function (config, callback) {
        var template = $.extend({}, {saved: 'saved <a href="${href}">${title}</a> by ${authors}'}, config.template),
            parseCiteulike = function (data) {
                var j, output = [], i = 0;
                if (data && data.length && data.length > 0) for (j = data.length; j > i; i++) {
                    var item = data[i];
                    output.push({
                        date: new Date(item.date),
                        config: config,
                        url: "http://www.citeulike.org/user/" + config.user,
                        html: $.tmpl(template.saved, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: "http://www.citeulike.org/json/user/" + config.user,
            dataType: "jsonp",
            success: function (data) {
                callback(parseCiteulike(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.dailymotion = function (config, callback) {
        var template = $.extend({}, {uploaded: 'uploaded a video <a href="${link}">${title[0]}</a>'}, config.template),
            parseDailymotion = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss.channel.item) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.uploaded, item)
                });
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.dailymotion.com/rss/user/' + config.user + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseDailymotion(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.delicious = function (config, callback) {
        var template = $.extend({}, {bookmarked: 'bookmarked <a href="${u}">${d}</a>'}, config.template);
        return $.ajax({
            url: "http://feeds.delicious.com/v2/json/" + config.user,
            dataType: "jsonp",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.length && data.length > 0) for (j = data.length; j > i; i++) {
                    var item = data[i];
                    output.push({date: new Date(item.dt), config: config, html: $.tmpl(template.bookmarked, item)})
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.deviantart = function (config, callback) {
        var template = $.extend({}, {posted: 'posted <a href="${link}">${title}</a>'}, config.template);
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select title,link,pubDate from rss where url="http://backend.deviantart.com/rss.xml?q=gallery%3A' + encodeURIComponent(config.user) + '&type=deviation" | unique(field="title")'),
            dataType: "jsonp",
            success: function (resp) {
                var items, item, j, output = [], i = 0;
                if (resp.query && resp.query.count > 0) for (items = resp.query.results.item, j = items.length; j > i; i++) item = items[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.posted, item)
                });
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.disqus = function (config, callback) {
        var template = $.extend({}, {
            post: 'commented on <a href="${url}">${thread.title}</a>',
            thread_like: 'liked <a href="${url}">${thread.title}</a>'
        }, config.template), parseDisqus = function (input) {
            var j, item, output = [], i = 0;
            if (input) for (j = input.length; j > i; i++) item = input[i], "reply" !== item.type && output.push({
                date: new Date(item.createdAt),
                config: config,
                html: $.tmpl(template[item.type], item.object)
            });
            return output
        };
        return $.ajax({
            url: "https://disqus.com/api/3.0/users/listActivity.json",
            data: {user: config.user, api_key: config.key},
            dataType: "jsonp",
            success: function (data) {
                return 2 === data.code ? (callback([]), console && console.error && console.error("Error loading Disqus stream.", data.response), void 0) : (callback(parseDisqus(data.response)), void 0)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.dribbble = function (config, callback) {
        var template = $.extend({}, {posted: 'posted a shot <a href="${url}">${title}</a>'}, config.template);
        return $.ajax({
            url: "http://api.dribbble.com/players/" + config.user + "/shots",
            dataType: "jsonp",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.total) for (j = data.shots.length; j > i; i++) {
                    var item = data.shots[i];
                    output.push({date: new Date(item.created_at), config: config, html: $.tmpl(template.posted, item)})
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.facebook_page = function (config, callback) {
        var template = $.extend({}, {wall_post: 'post on wall <a href="${link}">${title}</a>'}, config.template),
            parseFBPage = function (input) {
                var list, j, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) {
                    var item = list[i];
                    $.trim(item.title) && output.push({
                        date: new Date(item.pubDate),
                        config: config,
                        html: $.tmpl(template.wall_post, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="www.facebook.com/feeds/page.php?id=' + config.user + '&format=rss20"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseFBPage(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    "use strict";
    $.fn.lifestream.feeds.fancy = function (config, callback) {
        var template = $.extend({}, {fancied: 'fancy\'d <a href="${link}">${title}</a>'}, config.template),
            parseFancy = function (input) {
                var j, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) for (j = input.query.count; j > i; i++) {
                    var item = input.query.results.item[i];
                    output.push({date: new Date(item.pubDate), config: config, html: $.tmpl(template.fancied, item)})
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('SELECT * FROM xml WHERE url="http://www.fancy.com/rss/' + config.user + '" AND itemPath="/rss/channel/item"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseFancy(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.flickr = function (config, callback) {
        var template = $.extend({}, {posted: 'posted a photo <a href="${link}">${title}</a>'}, config.template);
        return $.ajax({
            url: "http://api.flickr.com/services/feeds/photos_public.gne?id=" + config.user + "&lang=en-us&format=json",
            dataType: "jsonp",
            jsonp: "jsoncallback",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.items && data.items.length > 0) for (j = data.items.length; j > i; i++) {
                    var item = data.items[i];
                    output.push({date: new Date(item.published), config: config, html: $.tmpl(template.posted, item)})
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.foomark = function (config, callback) {
        var template = $.extend({}, {bookmarked: 'bookmarked <a href="${url}">${url}</a>'}, config.template);
        return $.ajax({
            url: "http://api.foomark.com/urls/list/",
            data: {format: "jsonp", username: config.user},
            dataType: "jsonp",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.length && data.length > 0) for (j = data.length; j > i; i++) {
                    var item = data[i];
                    output.push({
                        date: new Date(item.created_at.replace(/-/g, "/")),
                        config: config,
                        html: $.tmpl(template.bookmarked, item)
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.formspring = function (config, callback) {
        var template = $.extend({}, {answered: 'answered a question <a href="${link}">${title}</a>'}, config.template),
            parseFormspring = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss.channel.item) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.answered, item)
                });
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.formspring.me/profile/' + config.user + '.rss"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseFormspring(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.forrst = function (config, callback) {
        var template = $.extend({}, {posted: 'posted a ${post_type} <a href="${post_url}">${title}</a>'}, config.template);
        return $.ajax({
            url: "http://forrst.com/api/v2/users/posts?username=" + config.user,
            dataType: "jsonp",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.resp.length && data.resp.length > 0) for (j = data.resp.length; j > i; i++) {
                    var item = data.resp[i];
                    output.push({
                        date: new Date(item.created_at.replace(" ", "T")),
                        config: config,
                        html: $.tmpl(template.posted, item)
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.foursquare = function (config, callback) {
        var template = $.extend({}, {checkedin: 'checked in @ <a href="${link}">${title}</a>'}, config.template),
            parseFoursquare = function (input) {
                var j, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) for (j = input.query.count; j > i; i++) {
                    var item = input.query.results.item[i];
                    output.push({date: new Date(item.pubDate), config: config, html: $.tmpl(template.checkedin, item)})
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from rss where url="https://feeds.foursquare.com/history/' + config.user + '.rss"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseFoursquare(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.gimmebar = function (config, callback) {
        var template = $.extend({}, {bookmarked: 'bookmarked <a href="${short_url}">${title}</a>'}, config.template);
        return $.ajax({
            url: "https://gimmebar.com/api/v0/public/assets/" + config.user + ".json?jsonp_callback=?",
            dataType: "json",
            success: function (data) {
                data = data.records;
                var j, output = [], i = 0;
                if (data && data.length && data.length > 0) for (j = data.length; j > i; i++) {
                    var item = data[i];
                    output.push({
                        date: new Date(1e3 * item.date),
                        config: config,
                        html: $.tmpl(template.bookmarked, item)
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.github = function (config, callback) {
        var template = $.extend({}, {
            commitCommentEvent: 'commented on <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            createBranchEvent: 'created branch <a href="http://github.com/${status.repo.name}/tree/${status.payload.ref}">${status.payload.ref}</a> at <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            createRepositoryEvent: 'created repository <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            createTagEvent: 'created tag <a href="http://github.com/${status.repo.name}/tree/${status.payload.ref}">${status.payload.ref}</a> at <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            deleteBranchEvent: 'deleted branch ${status.payload.ref} at <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            deleteTagEvent: 'deleted tag ${status.payload.ref} at <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            followEvent: 'started following <a href="http://github.com/${status.payload.target.login}">${status.payload.target.login}</a>',
            forkEvent: 'forked <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            gistEvent: '${status.payload.action} gist <a href="http://gist.github.com/${status.payload.gist.id}">${status.payload.gist.id}</a>',
            issueCommentEvent: 'commented on issue <a href="http://github.com/${status.repo.name}/issues/${status.payload.issue.number}">${status.payload.issue.number}</a> on <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            issuesEvent: '${status.payload.action} issue <a href="http://github.com/${status.repo.name}/issues/${status.payload.issue.number}">${status.payload.issue.number}</a> on <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            pullRequestEvent: '${status.payload.action} pull request <a href="http://github.com/${status.repo.name}/pull/${status.payload.number}">${status.payload.number}</a> on <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            pushEvent: 'pushed to <a href="http://github.com/${status.repo.name}/tree/${status.payload.ref}">${status.payload.ref}</a> at <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>',
            watchEvent: 'started watching <a href="http://github.com/${status.repo.name}">${status.repo.name}</a>'
        }, config.template), parseGithubStatus = function (status) {
            return "CommitCommentEvent" === status.type ? $.tmpl(template.commitCommentEvent, {status: status}) : "CreateEvent" === status.type && "branch" === status.payload.ref_type ? $.tmpl(template.createBranchEvent, {status: status}) : "CreateEvent" === status.type && "repository" === status.payload.ref_type ? $.tmpl(template.createRepositoryEvent, {status: status}) : "CreateEvent" === status.type && "tag" === status.payload.ref_type ? $.tmpl(template.createTagEvent, {status: status}) : "DeleteEvent" === status.type && "branch" === status.payload.ref_type ? $.tmpl(template.deleteBranchEvent, {status: status}) : "DeleteEvent" === status.type && "tag" === status.payload.ref_type ? $.tmpl(template.deleteTagEvent, {status: status}) : "FollowEvent" === status.type ? $.tmpl(template.followEvent, {status: status}) : "ForkEvent" === status.type ? $.tmpl(template.forkEvent, {status: status}) : "GistEvent" === status.type ? ("create" === status.payload.action ? status.payload.action = "created" : "update" === status.payload.action && (status.payload.action = "updated"), $.tmpl(template.gistEvent, {status: status})) : "IssueCommentEvent" === status.type ? $.tmpl(template.issueCommentEvent, {status: status}) : "IssuesEvent" === status.type ? $.tmpl(template.issuesEvent, {status: status}) : "PullRequestEvent" === status.type ? $.tmpl(template.pullRequestEvent, {status: status}) : "PushEvent" === status.type ? (status.payload.ref = status.payload.ref.split("/")[2], $.tmpl(template.pushEvent, {status: status})) : "WatchEvent" === status.type ? $.tmpl(template.watchEvent, {status: status}) : void 0
        }, parseGithub = function (input) {
            var j, output = [], i = 0;
            if (input.query && input.query.count && input.query.count > 0) for (j = input.query.count; j > i; i++) {
                var status = input.query.results.json[i].json;
                output.push({
                    date: new Date(status.created_at),
                    config: config,
                    html: parseGithubStatus(status),
                    url: "https://github.com/" + config.user
                })
            }
            return output
        };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select json.type, json.actor, json.repo, json.payload, json.created_at from json where url="https://api.github.com/users/' + config.user + '/events/public?per_page=100"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseGithub(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.googleplus = function (config, callback) {
        var template = $.extend({}, {posted: '<a href="${actor.url}">${actor.displayName}</a> has posted a new entry <a href="${url}" title="${id}">${title}</a> <!--With--> ${object.replies.totalItems} replies, ${object.plusoners.totalItems} +1s, ${object.resharers.totalItems} Reshares'}, config.template),
            parseGooglePlus = function (input) {
                var j, item, output = [], i = 0;
                if (input && input.items) for (j = input.items.length; j > i; i++) item = input.items[i], output.push({
                    date: new Date(item.published),
                    config: config,
                    html: $.tmpl(template.posted, item)
                });
                return output
            };
        return $.ajax({
            url: "https://www.googleapis.com/plus/v1/people/" + config.user + "/activities/public",
            data: {key: config.key},
            dataType: "jsonp",
            success: function (data) {
                return data.error ? (callback([]), console && console.error && console.error("Error loading Google+ stream.", data.error), void 0) : (callback(parseGooglePlus(data)), void 0)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.hypem = function (config, callback) {
        config.type && "history" === config.type && "loved" === config.type || (config.type = "loved");
        var template = $.extend({}, {
            loved: 'loved <a href="http://hypem.com/item/${mediaid}">${title}</a> by <a href="http://hypem.com/artist/${artist}">${artist}</a>',
            history: 'listened to <a href="http://hypem.com/item/${mediaid}">${title}</a> by <a href="http://hypem.com/artist/${artist}">${artist}</a>'
        }, config.template);
        return $.ajax({
            url: "http://hypem.com/playlist/" + config.type + "/" + config.user + "/json/1/data.js",
            dataType: "json",
            success: function (data) {
                var output = [], i = 0, j = -1;
                for (var k in data) data.hasOwnProperty(k) && j++;
                if (data && j > 0) for (; j > i; i++) {
                    var item = data[i];
                    output.push({
                        date: new Date(1e3 * ("history" === config.type ? item.dateplayed : item.dateloved)),
                        config: config,
                        html: $.tmpl("history" === config.type ? template.history : template.loved, item)
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.instapaper = function (config, callback) {
        var template = $.extend({}, {loved: 'loved <a href="${link}">${title}</a>'}, config.template),
            parseInstapaper = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss.channel.item) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.loved, item)
                });
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.instapaper.com/starred/rss/' + config.user + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseInstapaper(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.iusethis = function (config, callback) {
        var template = $.extend({}, {global: '${action} <a href="${link}">${what}</a> on (${os})'}, config.template),
            parseIusethis = function (input) {
                var list, i, j, k, l, n, item, title, actions, action, what, os, output = [], m = 0,
                    oss = ["iPhone", "OS X", "Windows"];
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss) for (n = input.query.results.rss.length, actions = ["started using", "stopped using", "stopped loving", "Downloaded", "commented on", "updated entry for", "started loving", "registered"], l = actions.length; n > m; m++) for (os = oss[m], list = input.query.results.rss[m].channel.item, i = 0, j = list.length; j > i; i++) {
                    for (item = list[i], title = item.title.replace(config.user + " ", ""), k = 0; l > k; k++) if (title.indexOf(actions[k]) > -1) {
                        action = actions[k];
                        break
                    }
                    what = title.split(action), output.push({
                        date: new Date(item.pubDate),
                        config: config,
                        html: $.tmpl(template.global, {
                            action: action.toLowerCase(),
                            link: item.link,
                            what: what[1],
                            os: os
                        })
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://iphone.iusethis.com/user/feed.rss/' + config.user + '" or url="http://osx.iusethis.com/user/feed.rss/' + config.user + '" or url="http://win.iusethis.com/user/feed.rss/' + config.user + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseIusethis(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.lastfm = function (config, callback) {
        var template = $.extend({}, {loved: 'loved <a href="${url}">${name}</a> by <a href="${artist.url}">${artist.name}</a>'}, config.template),
            parseLastfm = function (input) {
                var list, j, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.lovedtracks && input.query.results.lovedtracks.track) for (list = input.query.results.lovedtracks.track, j = list.length; j > i; i++) {
                    var item = list[i], itemDate = item.nowplaying ? new Date : item.date.uts;
                    output.push({
                        date: new Date(parseInt(1e3 * itemDate, 10)),
                        config: config,
                        html: $.tmpl(template.loved, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://ws.audioscrobbler.com/2.0/user/' + config.user + '/lovedtracks.xml"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseLastfm(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.librarything = function (config, callback) {
        var template = $.extend({}, {book: 'added <a href="http://www.librarything.com/work/book/${book.book_id}" title="${book.title} by ${book.author_fl}">${book.title} by ${book.author_fl}</a> to my library'}, config.template),
            parseLibraryThing = function (input) {
                var output = [], i = "";
                if (input.books) for (i in input.books) if (input.books.hasOwnProperty(i)) {
                    var book = input.books[i];
                    output.push({
                        date: new Date(1e3 * book.entry_stamp),
                        config: config,
                        html: $.tmpl(template.book, {book: book}),
                        url: "http://www.librarything.com/profile/" + config.user
                    })
                }
                return output
            };
        return $.ajax({
            url: "http://www.librarything.com/api_getdata.php?booksort=entry_REV&userid=" + config.user,
            dataType: "jsonp",
            success: function (data) {
                callback(parseLibraryThing(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    "use strict";
    $.fn.lifestream.feeds.linkedin = function (config, callback) {
        var template = $.extend({}, {posted: '<a href="${link}">${title}</a>'}, config.template),
            jsonpCallbackName = "jlsLinkedinCallback" + config.user, createYql = function () {
                var query = 'SELECT * FROM feed WHERE url="' + config.url + '"';
                return config.user && (query += ' AND link LIKE "%' + config.user + '%"'), query
            }, parseLinkedinItem = function (item) {
                return {date: new Date(item.pubDate), config: config, html: $.tmpl(template.posted, item)}
            };
        return window[jsonpCallbackName] = function (input) {
            var output = [], i = 0;
            if (input.query && input.query.count && input.query.count > 0) if (1 === input.query.count) output.push(parseLinkedinItem(input.query.results.item)); else for (i; i < input.query.count; i++) {
                var item = input.query.results.item[i];
                output.push(parseLinkedinItem(item))
            }
            callback(output)
        }, $.ajax({
            url: $.fn.lifestream.createYqlUrl(createYql()),
            cache: !0,
            data: {_maxage: 300},
            dataType: "jsonp",
            jsonpCallback: jsonpCallbackName
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.mendeley = function (config, callback) {
        var template = $.extend({}, {
            flagged1: 'flagged <a href="http://www.mendeley.com${link}">${title}</a>',
            flagged2: 'flagged <a href="${link}">${title}</a>'
        }, config.template), parseMendeley = function (input) {
            var list, j, output = [], i = 0;
            if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) {
                var item = list[i], tmplt = "/" === item.link.charAt(0) ? template.flagged1 : template.flagged2;
                output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    url: "http://mendeley.com/groups/" + config.user,
                    html: $.tmpl(tmplt, item)
                })
            }
            return output
        };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.mendeley.com/groups/' + config.user + '/feed/rss/"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseMendeley(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.miso = function (config, callback) {
        var template = $.extend({}, {watched: 'checked in to <a href="${link}">${title}</a>'}, config.template),
            parseMiso = function (input) {
                var list, j, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) {
                    var item = list[i];
                    output.push({
                        url: "http://www.gomiso.com/feeds/user/" + config.user + "/checkins.rss",
                        date: new Date(item.pubDate),
                        config: config,
                        html: $.tmpl(template.watched, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.gomiso.com/feeds/user/' + config.user + '/checkins.rss"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseMiso(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.mlkshk = function (config, callback) {
        var template = $.extend({}, {posted: 'posted <a href="${link}">${title}</a>'}, config.template),
            parseMlkshk = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss.channel.item) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.posted, item)
                });
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://mlkshk.com/user/' + config.user + '/rss"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseMlkshk(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.pinboard = function (config, callback) {
        var template = $.extend({}, {bookmarked: 'bookmarked <a href="${link}">${title}</a>'}, config.template),
            parsePinboard = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.RDF.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.date),
                    config: config,
                    html: $.tmpl(template.bookmarked, item)
                });
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://feeds.pinboard.in/rss/u:' + config.user + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parsePinboard(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.pocket = function (config, callback) {
        var template = $.extend({}, {pocketed: 'pocketed <a href="${link}">${title}</a>'}, config.template),
            parsePocket = function (input) {
                var list, j, output = [], i = 0;
                if (input.query && input.query.results) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) {
                    var item = list[i], tmplt = template.pocketed;
                    output.push({
                        date: new Date(item.pubDate),
                        config: config,
                        url: "http://getpocket.com",
                        html: $.tmpl(tmplt, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.getpocket.com/users/' + config.user + '/feed/all/"'),
            dataType: "json",
            success: function (data) {
                callback(parsePocket(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.posterous = function (config, callback) {
        var template = $.extend({}, {posted: 'posted <a href="${link}">${title}</a>'}, config.template),
            parsePosterous = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss.channel.item) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.posted, item)
                });
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://' + config.user + '.posterous.com/rss.xml"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parsePosterous(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.quora = function (config, callback) {
        var template = $.extend({}, {posted: '<a href="${link}">${title}</a>'}, config.template),
            getChannelUrl = function (channel) {
                for (var i = 0, j = channel.link.length; j > i; i++) {
                    var link = channel.link[i];
                    if ("string" == typeof link) return link
                }
                return ""
            }, parseRSS = function (input) {
                var output = [], list = [], i = 0, j = 0, url = "";
                if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.rss.channel.item, j = list.length, url = getChannelUrl(input.query.results.rss.channel); j > i; i++) {
                    var item = list[i];
                    output.push({
                        url: url,
                        date: new Date(item.pubDate),
                        config: config,
                        html: $.tmpl(template.posted, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.quora.com/' + config.user + '/rss"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseRSS(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.reddit = function (config, callback) {
        var template = $.extend({}, {
            commented: '<a href="http://www.reddit.com/r/${item.data.subreddit}/comments/${item.data.link_id.substring(3)}/u/${item.data.name.substring(3)}?context=3">commented (${score})</a> in <a href="http://www.reddit.com/r/${item.data.subreddit}">${item.data.subreddit}</a>',
            created: '<a href="http://www.reddit.com${item.data.permalink}">created new thread (${score})</a> in <a href="http://www.reddit.com/r/${item.data.subreddit}">${item.data.subreddit}</a>'
        }, config.template), parseRedditItem = function (item) {
            var score = item.data.ups - item.data.downs, pass = {item: item, score: score > 0 ? "+" + score : score};
            return "t1" === item.kind ? $.tmpl(template.commented, pass) : "t3" === item.kind ? $.tmpl(template.created, pass) : void 0
        }, convertDate = function (date) {
            return new Date(1e3 * date)
        };
        return $.ajax({
            url: "http://www.reddit.com/user/" + config.user + ".json",
            dataType: "jsonp",
            jsonp: "jsonp",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.data && data.data.children && data.data.children.length > 0) for (j = data.data.children.length; j > i; i++) {
                    var item = data.data.children[i];
                    output.push({
                        date: convertDate(item.data.created_utc),
                        config: config,
                        html: parseRedditItem(item),
                        url: "http://reddit.com/user/" + config.user
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.rss = function (config, callback) {
        var template = $.extend({}, {posted: 'posted <a href="${link}">${title}</a>'}, config.template),
            getChannelUrl = function (channel) {
                for (var i = 0, j = channel.link.length; j > i; i++) {
                    var link = channel.link[i];
                    if ("string" == typeof link) return link
                }
                return ""
            }, parseRSS = function (input) {
                var output = [], list = [], i = 0, j = 0, url = "";
                if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.rss.channel.item, j = list.length, url = getChannelUrl(input.query.results.rss.channel); j > i; i++) {
                    var item = list[i];
                    output.push({
                        url: url,
                        date: new Date(item.pubDate),
                        config: config,
                        html: $.tmpl(template.posted, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="' + config.user + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseRSS(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.slideshare = function (config, callback) {
        var template = $.extend({}, {uploaded: 'uploaded a presentation <a href="${link}">${title}</a>'}, config.template),
            parseSlideshare = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.uploaded, item)
                });
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://www.slideshare.net/rss/user/' + config.user + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseSlideshare(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.snipplr = function (config, callback) {
        var template = $.extend({}, {posted: 'posted a snippet <a href="${link}">${title}</a>'}, config.template),
            parseSnipplr = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss.channel.item) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.posted, item)
                });
                return output
            };
        $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="http://snipplr.com/rss/users/' + config.user + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseSnipplr(data))
            }
        })
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.stackoverflow = function (config, callback) {
        var template = $.extend({}, {global: '<a href="${link}">${text}</a> - ${title}'}, config.template),
            parseStackoverflowItem = function (item) {
                var text = "", title = "", link = "",
                    stackoverflow_link = "http://stackoverflow.com/users/" + config.user,
                    question_link = "http://stackoverflow.com/questions/";
                return "badge" === item.timeline_type ? (text = "was " + item.action + " the '" + item.description + "' badge", title = item.detail, link = stackoverflow_link + "?tab=reputation") : "comment" === item.timeline_type ? (text = "commented on", title = item.description, link = question_link + item.post_id) : ("revision" === item.timeline_type || "accepted" === item.timeline_type || "askoranswered" === item.timeline_type) && (text = "askoranswered" === item.timeline_type ? item.action : item.action + " " + item.post_type, title = item.detail || item.description || "", link = question_link + item.post_id), {
                    link: link,
                    title: title,
                    text: text
                }
            }, convertDate = function (date) {
                return new Date(1e3 * date)
            };
        return $.ajax({
            url: "http://api.stackoverflow.com/1.1/users/" + config.user + "/timeline?jsonp",
            dataType: "jsonp",
            jsonp: "jsonp",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.total && data.total > 0 && data.user_timelines) for (j = data.user_timelines.length; j > i; i++) {
                    var item = data.user_timelines[i];
                    output.push({
                        date: convertDate(item.creation_date),
                        config: config,
                        html: $.tmpl(template.global, parseStackoverflowItem(item))
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.tumblr = function (config, callback) {
        var template = $.extend({}, {posted: 'posted a ${type} <a href="${url}">${title}</a>'}, config.template),
            limit = config.limit || 20, getImage = function (post) {
                switch (post.type) {
                    case"photo":
                        var images = post["photo-url"];
                        return $('<img width="75" height="75"/>').attr({
                            src: images[images.length - 1].content,
                            title: getTitle(post),
                            alt: getTitle(post)
                        }).wrap("<div/>").parent().html();
                    case"video":
                        var videos = post["video-player"], video = videos[videos.length - 1].content;
                        return video.match(/<\s*script/) ? null : video;
                    case"audio":
                        return post["audio-player"] + " " + $("<div/>").text(getTitle(post)).html();
                    default:
                        return null
                }
            }, getFirstElementOfBody = function (post, bodyAttribute) {
                return $(post[bodyAttribute]).filter(":not(:empty):first").text()
            }, getTitleForPostType = function (post) {
                var title;
                switch (post.type) {
                    case"regular":
                        return post["regular-title"] || getFirstElementOfBody(post, "regular-body");
                    case"link":
                        return title = post["link-text"] || getFirstElementOfBody(post, "link-description"), "" === title && (title = post["link-url"]), title;
                    case"video":
                        return getFirstElementOfBody(post, "video-caption");
                    case"audio":
                        return getFirstElementOfBody(post, "audio-caption");
                    case"photo":
                        return getFirstElementOfBody(post, "photo-caption");
                    case"quote":
                        return '"' + post["quote-text"].replace(/<.+?>/g, " ").trim() + '"';
                    case"conversation":
                        return title = post["conversation-title"], title || (title = post.conversation.line, "string" != typeof title && (title = title[0].label + " " + title[0].content + " ...")), title;
                    case"answer":
                        return post.question;
                    default:
                        return post.type
                }
            }, getTitle = function (post) {
                var title = getTitleForPostType(post) || "";
                return title.replace(/<.+?>/gi, " ")
            }, createTumblrOutput = function (config, post) {
                return {
                    date: new Date(post.date),
                    config: config,
                    html: $.tmpl(template.posted, {
                        type: post.type.replace("regular", "blog entry"),
                        url: post.url,
                        image: getImage(post),
                        title: getTitle(post)
                    })
                }
            }, parseTumblr = function (input) {
                var j, post, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) if ($.isArray(input.query.results.posts.post)) for (j = input.query.results.posts.post.length; j > i; i++) post = input.query.results.posts.post[i], output.push(createTumblrOutput(config, post)); else $.isPlainObject(input.query.results.posts.post) && output.push(createTumblrOutput(config, input.query.results.posts.post));
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from tumblr.posts where username="' + config.user + '" and num="' + limit + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseTumblr(data))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    "use strict";
    $.fn.lifestream.feeds.twitter = function (config, callback) {
        var template = $.extend({}, {posted: "{{html tweet}}"}, config.template),
            jsonpCallbackName = "jlsTwitterCallback" + config.user.replace(/[^a-zA-Z0-9]+/g, ""),
            linkify = function (tweet) {
                var link = function (t) {
                    return t.replace(/[a-z]+:\/\/[a-z0-9\-_]+\.[a-z0-9\-_:~%&\?\/.=]+[^:\.,\)\s*$]/gi, function (m) {
                        return '<a href="' + m + '">' + (m.length > 25 ? m.substr(0, 24) + "..." : m) + "</a>"
                    })
                }, at = function (t) {
                    return t.replace(/(^|[^\w]+)\@([a-zA-Z0-9_]{1,15})/g, function (m, m1, m2) {
                        return m1 + '<a href="http://twitter.com/' + m2 + '">@' + m2 + "</a>"
                    })
                }, hash = function (t) {
                    return t.replace(/(^|[^\w'"]+)\#([a-zA-Z0-9脜氓脛盲脰枚脴酶脝忙脡茅脠猫脺眉脢锚脹没脦卯_]+)/g, function (m, m1, m2) {
                        return m1 + '<a href="http://search.twitter.com/search?q=%23' + m2 + '">#' + m2 + "</a>"
                    })
                };
                return hash(at(link(tweet)))
            }, parseTwitter = function (items) {
                var output = [], i = 0, j = items.length;
                for (i; j > i; i++) {
                    var status = items[i];
                    output.push({
                        date: new Date(1e3 * status.created_at),
                        config: config,
                        html: $.tmpl(template.posted, {
                            tweet: linkify(status.text),
                            complete_url: "http://twitter.com/" + config.user + "/status/" + status.id_str
                        }),
                        url: "http://twitter.com/" + config.user
                    })
                }
                return output
            };
        return window[jsonpCallbackName] = function (data) {
            data.query && data.query.count > 0 && callback(parseTwitter(data.query.results.items))
        }, $.ajax({
            url: $.fn.lifestream.createYqlUrl('USE "http://arminrosu.github.io/twitter-open-data-table/table.xml" AS twitter; SELECT * FROM twitter WHERE screen_name = "' + config.user + '"'),
            cache: !0,
            data: {_maxage: 300},
            dataType: "jsonp",
            jsonpCallback: jsonpCallbackName
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.vimeo = function (config, callback) {
        var template = $.extend({}, {
            liked: 'liked <a href="${url}" title="${description}">${title}</a>',
            posted: 'posted <a href="${url}" title="${description}">${title}</a>'
        }, config.template), parseVimeo = function (input, item_type) {
            var j, item, date, description, output = [], i = 0, type = item_type || "liked";
            if (input) for (j = input.length; j > i; i++) item = input[i], date = "posted" === type ? new Date(item.upload_date.replace(" ", "T")) : new Date(item.liked_on.replace(" ", "T")), description = item.description ? item.description.replace(/"/g, "'").replace(/<.+?>/gi, "") : "", output.push({
                date: date,
                config: config,
                html: $.tmpl(template[type], {
                    url: item.url,
                    description: item.description ? item.description.replace(/"/g, "'").replace(/<.+?>/gi, "") : "",
                    title: item.title
                })
            });
            return output
        };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('SELECT * FROM xml WHERE url="http://vimeo.com/api/v2/' + config.user + '/likes.xml" OR url="http://vimeo.com/api/v2/' + config.user + '/videos.xml"'),
            dataType: "jsonp",
            success: function (response) {
                var output = [];
                response.query.results.videos[0].video.length > 0 && (output = output.concat(parseVimeo(response.query.results.videos[0].video))), response.query.results.videos[1].video.length > 0 && (output = output.concat(parseVimeo(response.query.results.videos[1].video, "posted"))), callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.wikipedia = function (config, callback) {
        var language = config.language || "en",
            template = $.extend({}, {contribution: 'contributed to <a href="${url}">${title}</a>'}, config.template);
        return $.ajax({
            url: "http://" + language + ".wikipedia.org/w/api.php?action=query&ucuser=" + config.user + "&list=usercontribs&ucdir=older&format=json",
            dataType: "jsonp",
            success: function (data) {
                var j, output = [], i = 0;
                if (data && data.query.usercontribs) for (j = data.query.usercontribs.length; j > i; i++) {
                    var item = data.query.usercontribs[i];
                    item.url = "http://" + language + ".wikipedia.org/wiki/" + item.title.replace(" ", "_"), output.push({
                        date: new Date(item.timestamp),
                        config: config,
                        html: $.tmpl(template.contribution, item)
                    })
                }
                callback(output)
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.wordpress = function (config, callback) {
        var template = $.extend({}, {posted: 'posted <a href="${link}">${title}</a>'}, config.template),
            parseWordpress = function (input) {
                var list, j, item, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0 && input.query.results.rss.channel.item) for (list = input.query.results.rss.channel.item, j = list.length; j > i; i++) item = list[i], output.push({
                    date: new Date(item.pubDate),
                    config: config,
                    html: $.tmpl(template.posted, item)
                });
                return output
            }, url = "";
        return config.user && (url = 0 === config.user.indexOf("http://") ? config.user + "/feed" : "http://" + config.user + ".wordpress.com/feed", $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="' + url + '"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseWordpress(data))
            }
        })), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.youtube = function (config, callback) {
        var template = $.extend({}, {
            uploaded: 'uploaded <a href="${video.player.default}" title="${video.description}">${video.title}</a>',
            favorited: 'favorited <a href="${video.player.default}" title="${video.description}">${video.title}</a>'
        }, config.template), parseYoutube = function (input, activity) {
            var j, item, video, date, templateData, output = [], i = 0;
            if (input.data && input.data.items) for (j = input.data.items.length; j > i; i++) {
                switch (item = input.data.items[i], activity) {
                    case"favorited":
                        video = item.video, date = item.created, templateData = item;
                        break;
                    case"uploaded":
                        video = item, date = video.uploaded, templateData = {video: video}
                }
                video.player && video.player["default"] && output.push({
                    date: new Date(date),
                    config: config,
                    html: $.tmpl(template[activity], templateData)
                })
            }
            return output
        };
        return $.ajax({
            url: "http://gdata.youtube.com/feeds/api/users/" + config.user + "/favorites?v=2&alt=jsonc",
            dataType: "jsonp",
            success: function (data) {
                callback(parseYoutube(data, "favorited"))
            }
        }), $.ajax({
            url: "http://gdata.youtube.com/feeds/api/users/" + config.user + "/uploads?v=2&alt=jsonc",
            dataType: "jsonp",
            success: function (data) {
                callback(parseYoutube(data, "uploaded"))
            }
        }), {template: template}
    }
}(jQuery), function ($) {
    $.fn.lifestream.feeds.zotero = function (config, callback) {
        var template = $.extend({}, {flagged: 'flagged <a href="${id}">${title}</a> by ${creatorSummary}'}, config.template),
            parseZotero = function (input) {
                var list, j, output = [], i = 0;
                if (input.query && input.query.count && input.query.count > 0) for (list = input.query.results.feed.entry, j = list.length; j > i; i++) {
                    var item = list[i];
                    output.push({
                        date: new Date(item.updated),
                        config: config,
                        url: "http://zotero.com/users/" + config.user,
                        html: $.tmpl(template.flagged, item)
                    })
                }
                return output
            };
        return $.ajax({
            url: $.fn.lifestream.createYqlUrl('select * from xml where url="https://api.zotero.org/users/' + config.user + '/items"'),
            dataType: "jsonp",
            success: function (data) {
                callback(parseZotero(data))
            }
        }), {template: template}
    }
}(jQuery);