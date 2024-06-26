!function ($) {
    function refresh() {
        var data = prepareData(this);
        return isNaN(data.datetime) || $(this).text(inWords(data.datetime)), this
    }

    function prepareData(element) {
        if (element = $(element), !element.data("timeago")) {
            element.data("timeago", {datetime: $t.datetime(element)});
            var text = $.trim(element.text());
            !(text.length > 0) || $t.isTime(element) && element.attr("title") || element.attr("title", text)
        }
        return element.data("timeago")
    }

    function inWords(date) {
        return $t.inWords(distance(date))
    }

    function distance(date) {
        return (new Date).getTime() - date.getTime()
    }

    $.timeago = function (timestamp) {
        return timestamp instanceof Date ? inWords(timestamp) : "string" == typeof timestamp ? inWords($.timeago.parse(timestamp)) : "number" == typeof timestamp ? inWords(new Date(timestamp)) : inWords($.timeago.datetime(timestamp))
    };
    var $t = $.timeago;
    $.extend($.timeago, {
        settings: {
            refreshMillis: 6e4,
            allowFuture: !1,
            strings: {
                prefixAgo: null,
                prefixFromNow: null,
                suffixAgo: "ago",
                suffixFromNow: "from now",
                seconds: "less than a minute",
                minute: "about a minute",
                minutes: "%d minutes",
                hour: "about an hour",
                hours: "about %d hours",
                day: "a day",
                days: "%d days",
                month: "about a month",
                months: "%d months",
                year: "about a year",
                years: "%d years",
                wordSeparator: " ",
                numbers: []
            }
        }, inWords: function (distanceMillis) {
            function substitute(stringOrFunction, number) {
                var string = $.isFunction(stringOrFunction) ? stringOrFunction(number, distanceMillis) : stringOrFunction,
                    value = $l.numbers && $l.numbers[number] || number;
                return string.replace(/%d/i, value)
            }

            var $l = this.settings.strings, prefix = $l.prefixAgo, suffix = $l.suffixAgo;
            this.settings.allowFuture && 0 > distanceMillis && (prefix = $l.prefixFromNow, suffix = $l.suffixFromNow);
            var seconds = Math.abs(distanceMillis) / 1e3, minutes = seconds / 60, hours = minutes / 60,
                days = hours / 24, years = days / 365,
                words = 45 > seconds && substitute($l.seconds, Math.round(seconds)) || 90 > seconds && substitute($l.minute, 1) || 45 > minutes && substitute($l.minutes, Math.round(minutes)) || 90 > minutes && substitute($l.hour, 1) || 24 > hours && substitute($l.hours, Math.round(hours)) || 42 > hours && substitute($l.day, 1) || 30 > days && substitute($l.days, Math.round(days)) || 45 > days && substitute($l.month, 1) || 365 > days && substitute($l.months, Math.round(days / 30)) || 1.5 > years && substitute($l.year, 1) || substitute($l.years, Math.round(years)),
                separator = void 0 === $l.wordSeparator ? " " : $l.wordSeparator;
            return $.trim([prefix, words, suffix].join(separator))
        }, parse: function (iso8601) {
            var s = $.trim(iso8601);
            return s = s.replace(/\.\d\d\d+/, ""), s = s.replace(/-/, "/").replace(/-/, "/"), s = s.replace(/T/, " ").replace(/Z/, " UTC"), s = s.replace(/([\+\-]\d\d)\:?(\d\d)/, " $1$2"), new Date(s)
        }, datetime: function (elem) {
            var iso8601 = $t.isTime(elem) ? $(elem).attr("datetime") : $(elem).attr("title");
            return $t.parse(iso8601)
        }, isTime: function (elem) {
            return "time" === $(elem).get(0).tagName.toLowerCase()
        }
    }), $.fn.timeago = function () {
        var self = this;
        self.each(refresh);
        var $s = $t.settings;
        return $s.refreshMillis > 0 && setInterval(function () {
            self.each(refresh)
        }, $s.refreshMillis), self
    }, document.createElement("abbr"), document.createElement("time")
}(jQuery);