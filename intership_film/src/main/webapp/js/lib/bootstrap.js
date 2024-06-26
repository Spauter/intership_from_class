if ("undefined" == typeof jQuery) throw new Error("Bootstrap requires jQuery");
+function ($) {
    "use strict";

    function transitionEnd() {
        var el = document.createElement("bootstrap"), transEndEventNames = {
            WebkitTransition: "webkitTransitionEnd",
            MozTransition: "transitionend",
            OTransition: "oTransitionEnd otransitionend",
            transition: "transitionend"
        };
        for (var name in transEndEventNames) if (void 0 !== el.style[name]) return {end: transEndEventNames[name]}
    }

    $.fn.emulateTransitionEnd = function (duration) {
        var called = !1, $el = this;
        $(this).one($.support.transition.end, function () {
            called = !0
        });
        var callback = function () {
            called || $($el).trigger($.support.transition.end)
        };
        return setTimeout(callback, duration), this
    }, $(function () {
        $.support.transition = transitionEnd()
    })
}(jQuery), +function ($) {
    "use strict";
    var dismiss = '[data-dismiss="alert"]', Alert = function (el) {
        $(el).on("click", dismiss, this.close)
    };
    Alert.prototype.close = function (e) {
        function removeElement() {
            $parent.trigger("closed.bs.alert").remove()
        }

        var $this = $(this), selector = $this.attr("data-target");
        selector || (selector = $this.attr("href"), selector = selector && selector.replace(/.*(?=#[^\s]*$)/, ""));
        var $parent = $(selector);
        e && e.preventDefault(), $parent.length || ($parent = $this.hasClass("alert") ? $this : $this.parent()), $parent.trigger(e = $.Event("close.bs.alert")), e.isDefaultPrevented() || ($parent.removeClass("in"), $.support.transition && $parent.hasClass("fade") ? $parent.one($.support.transition.end, removeElement).emulateTransitionEnd(150) : removeElement())
    };
    var old = $.fn.alert;
    $.fn.alert = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.alert");
            data || $this.data("bs.alert", data = new Alert(this)), "string" == typeof option && data[option].call($this)
        })
    }, $.fn.alert.Constructor = Alert, $.fn.alert.noConflict = function () {
        return $.fn.alert = old, this
    }, $(document).on("click.bs.alert.data-api", dismiss, Alert.prototype.close)
}(jQuery), +function ($) {
    "use strict";
    var Button = function (element, options) {
        this.$element = $(element), this.options = $.extend({}, Button.DEFAULTS, options)
    };
    Button.DEFAULTS = {loadingText: "loading..."}, Button.prototype.setState = function (state) {
        var d = "disabled", $el = this.$element, val = $el.is("input") ? "val" : "html", data = $el.data();
        state += "Text", data.resetText || $el.data("resetText", $el[val]()), $el[val](data[state] || this.options[state]), setTimeout(function () {
            "loadingText" == state ? $el.addClass(d).attr(d, d) : $el.removeClass(d).removeAttr(d)
        }, 0)
    }, Button.prototype.toggle = function () {
        var $parent = this.$element.closest('[data-toggle="buttons"]');
        if ($parent.length) {
            var $input = this.$element.find("input").prop("checked", !this.$element.hasClass("active")).trigger("change");
            "radio" === $input.prop("type") && $parent.find(".active").removeClass("active")
        }
        this.$element.toggleClass("active")
    };
    var old = $.fn.button;
    $.fn.button = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.button"), options = "object" == typeof option && option;
            data || $this.data("bs.button", data = new Button(this, options)), "toggle" == option ? data.toggle() : option && data.setState(option)
        })
    }, $.fn.button.Constructor = Button, $.fn.button.noConflict = function () {
        return $.fn.button = old, this
    }, $(document).on("click.bs.button.data-api", "[data-toggle^=button]", function (e) {
        var $btn = $(e.target);
        $btn.hasClass("btn") || ($btn = $btn.closest(".btn")), $btn.button("toggle"), e.preventDefault()
    })
}(jQuery), +function ($) {
    "use strict";
    var Carousel = function (element, options) {
        this.$element = $(element), this.$indicators = this.$element.find(".carousel-indicators"), this.options = options, this.paused = this.sliding = this.interval = this.$active = this.$items = null, "hover" == this.options.pause && this.$element.on("mouseenter", $.proxy(this.pause, this)).on("mouseleave", $.proxy(this.cycle, this))
    };
    Carousel.DEFAULTS = {interval: 5e3, pause: "hover", wrap: !0}, Carousel.prototype.cycle = function (e) {
        return e || (this.paused = !1), this.interval && clearInterval(this.interval), this.options.interval && !this.paused && (this.interval = setInterval($.proxy(this.next, this), this.options.interval)), this
    }, Carousel.prototype.getActiveIndex = function () {
        return this.$active = this.$element.find(".item.active"), this.$items = this.$active.parent().children(), this.$items.index(this.$active)
    }, Carousel.prototype.to = function (pos) {
        var that = this, activeIndex = this.getActiveIndex();
        return pos > this.$items.length - 1 || 0 > pos ? void 0 : this.sliding ? this.$element.one("slid", function () {
            that.to(pos)
        }) : activeIndex == pos ? this.pause().cycle() : this.slide(pos > activeIndex ? "next" : "prev", $(this.$items[pos]))
    }, Carousel.prototype.pause = function (e) {
        return e || (this.paused = !0), this.$element.find(".next, .prev").length && $.support.transition.end && (this.$element.trigger($.support.transition.end), this.cycle(!0)), this.interval = clearInterval(this.interval), this
    }, Carousel.prototype.next = function () {
        return this.sliding ? void 0 : this.slide("next")
    }, Carousel.prototype.prev = function () {
        return this.sliding ? void 0 : this.slide("prev")
    }, Carousel.prototype.slide = function (type, next) {
        var $active = this.$element.find(".item.active"), $next = next || $active[type](), isCycling = this.interval,
            direction = "next" == type ? "left" : "right", fallback = "next" == type ? "first" : "last", that = this;
        if (!$next.length) {
            if (!this.options.wrap) return;
            $next = this.$element.find(".item")[fallback]()
        }
        this.sliding = !0, isCycling && this.pause();
        var e = $.Event("slide.bs.carousel", {relatedTarget: $next[0], direction: direction});
        if (!$next.hasClass("active")) {
            if (this.$indicators.length && (this.$indicators.find(".active").removeClass("active"), this.$element.one("slid", function () {
                var $nextIndicator = $(that.$indicators.children()[that.getActiveIndex()]);
                $nextIndicator && $nextIndicator.addClass("active")
            })), $.support.transition && this.$element.hasClass("slide")) {
                if (this.$element.trigger(e), e.isDefaultPrevented()) return;
                $next.addClass(type), $next[0].offsetWidth, $active.addClass(direction), $next.addClass(direction), $active.one($.support.transition.end, function () {
                    $next.removeClass([type, direction].join(" ")).addClass("active"), $active.removeClass(["active", direction].join(" ")), that.sliding = !1, setTimeout(function () {
                        that.$element.trigger("slid")
                    }, 0)
                }).emulateTransitionEnd(600)
            } else {
                if (this.$element.trigger(e), e.isDefaultPrevented()) return;
                $active.removeClass("active"), $next.addClass("active"), this.sliding = !1, this.$element.trigger("slid")
            }
            return isCycling && this.cycle(), this
        }
    };
    var old = $.fn.carousel;
    $.fn.carousel = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.carousel"),
                options = $.extend({}, Carousel.DEFAULTS, $this.data(), "object" == typeof option && option),
                action = "string" == typeof option ? option : options.slide;
            data || $this.data("bs.carousel", data = new Carousel(this, options)), "number" == typeof option ? data.to(option) : action ? data[action]() : options.interval && data.pause().cycle()
        })
    }, $.fn.carousel.Constructor = Carousel, $.fn.carousel.noConflict = function () {
        return $.fn.carousel = old, this
    }, $(document).on("click.bs.carousel.data-api", "[data-slide], [data-slide-to]", function (e) {
        var href, $this = $(this),
            $target = $($this.attr("data-target") || (href = $this.attr("href")) && href.replace(/.*(?=#[^\s]+$)/, "")),
            options = $.extend({}, $target.data(), $this.data()), slideIndex = $this.attr("data-slide-to");
        slideIndex && (options.interval = !1), $target.carousel(options), (slideIndex = $this.attr("data-slide-to")) && $target.data("bs.carousel").to(slideIndex), e.preventDefault()
    }), $(window).on("load", function () {
        $('[data-ride="carousel"]').each(function () {
            var $carousel = $(this);
            $carousel.carousel($carousel.data())
        })
    })
}(jQuery), +function ($) {
    "use strict";
    var Collapse = function (element, options) {
        this.$element = $(element), this.options = $.extend({}, Collapse.DEFAULTS, options), this.transitioning = null, this.options.parent && (this.$parent = $(this.options.parent)), this.options.toggle && this.toggle()
    };
    Collapse.DEFAULTS = {toggle: !0}, Collapse.prototype.dimension = function () {
        var hasWidth = this.$element.hasClass("width");
        return hasWidth ? "width" : "height"
    }, Collapse.prototype.show = function () {
        if (!this.transitioning && !this.$element.hasClass("in")) {
            var startEvent = $.Event("show.bs.collapse");
            if (this.$element.trigger(startEvent), !startEvent.isDefaultPrevented()) {
                var actives = this.$parent && this.$parent.find("> .panel > .in");
                if (actives && actives.length) {
                    var hasData = actives.data("bs.collapse");
                    if (hasData && hasData.transitioning) return;
                    actives.collapse("hide"), hasData || actives.data("bs.collapse", null)
                }
                var dimension = this.dimension();
                this.$element.removeClass("collapse").addClass("collapsing")[dimension](0), this.transitioning = 1;
                var complete = function () {
                    this.$element.removeClass("collapsing").addClass("in")[dimension]("auto"), this.transitioning = 0, this.$element.trigger("shown.bs.collapse")
                };
                if (!$.support.transition) return complete.call(this);
                var scrollSize = $.camelCase(["scroll", dimension].join("-"));
                this.$element.one($.support.transition.end, $.proxy(complete, this)).emulateTransitionEnd(350)[dimension](this.$element[0][scrollSize])
            }
        }
    }, Collapse.prototype.hide = function () {
        if (!this.transitioning && this.$element.hasClass("in")) {
            var startEvent = $.Event("hide.bs.collapse");
            if (this.$element.trigger(startEvent), !startEvent.isDefaultPrevented()) {
                var dimension = this.dimension();
                this.$element[dimension](this.$element[dimension]())[0].offsetHeight, this.$element.addClass("collapsing").removeClass("collapse").removeClass("in"), this.transitioning = 1;
                var complete = function () {
                    this.transitioning = 0, this.$element.trigger("hidden.bs.collapse").removeClass("collapsing").addClass("collapse")
                };
                return $.support.transition ? (this.$element[dimension](0).one($.support.transition.end, $.proxy(complete, this)).emulateTransitionEnd(350), void 0) : complete.call(this)
            }
        }
    }, Collapse.prototype.toggle = function () {
        this[this.$element.hasClass("in") ? "hide" : "show"]()
    };
    var old = $.fn.collapse;
    $.fn.collapse = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.collapse"),
                options = $.extend({}, Collapse.DEFAULTS, $this.data(), "object" == typeof option && option);
            data || $this.data("bs.collapse", data = new Collapse(this, options)), "string" == typeof option && data[option]()
        })
    }, $.fn.collapse.Constructor = Collapse, $.fn.collapse.noConflict = function () {
        return $.fn.collapse = old, this
    }, $(document).on("click.bs.collapse.data-api", "[data-toggle=collapse]", function (e) {
        var href, $this = $(this),
            target = $this.attr("data-target") || e.preventDefault() || (href = $this.attr("href")) && href.replace(/.*(?=#[^\s]+$)/, ""),
            $target = $(target), data = $target.data("bs.collapse"), option = data ? "toggle" : $this.data(),
            parent = $this.attr("data-parent"), $parent = parent && $(parent);
        data && data.transitioning || ($parent && $parent.find('[data-toggle=collapse][data-parent="' + parent + '"]').not($this).addClass("collapsed"), $this[$target.hasClass("in") ? "addClass" : "removeClass"]("collapsed")), $target.collapse(option)
    })
}(jQuery), +function ($) {
    "use strict";

    function clearMenus() {
        $(backdrop).remove(), $(toggle).each(function (e) {
            var $parent = getParent($(this));
            $parent.hasClass("open") && ($parent.trigger(e = $.Event("hide.bs.dropdown")), e.isDefaultPrevented() || $parent.removeClass("open").trigger("hidden.bs.dropdown"))
        })
    }

    function getParent($this) {
        var selector = $this.attr("data-target");
        selector || (selector = $this.attr("href"), selector = selector && /#/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, ""));
        var $parent = selector && $(selector);
        return $parent && $parent.length ? $parent : $this.parent()
    }

    var backdrop = ".dropdown-backdrop", toggle = "[data-toggle=dropdown]", Dropdown = function (element) {
        $(element).on("click.bs.dropdown", this.toggle)
    };
    Dropdown.prototype.toggle = function (e) {
        var $this = $(this);
        if (!$this.is(".disabled, :disabled")) {
            var $parent = getParent($this), isActive = $parent.hasClass("open");
            if (clearMenus(), !isActive) {
                if ("ontouchstart" in document.documentElement && !$parent.closest(".navbar-nav").length && $('<div class="dropdown-backdrop"/>').insertAfter($(this)).on("click", clearMenus), $parent.trigger(e = $.Event("show.bs.dropdown")), e.isDefaultPrevented()) return;
                $parent.toggleClass("open").trigger("shown.bs.dropdown"), $this.focus()
            }
            return !1
        }
    }, Dropdown.prototype.keydown = function (e) {
        if (/(38|40|27)/.test(e.keyCode)) {
            var $this = $(this);
            if (e.preventDefault(), e.stopPropagation(), !$this.is(".disabled, :disabled")) {
                var $parent = getParent($this), isActive = $parent.hasClass("open");
                if (!isActive || isActive && 27 == e.keyCode) return 27 == e.which && $parent.find(toggle).focus(), $this.click();
                var $items = $("[role=menu] li:not(.divider):visible a", $parent);
                if ($items.length) {
                    var index = $items.index($items.filter(":focus"));
                    38 == e.keyCode && index > 0 && index--, 40 == e.keyCode && index < $items.length - 1 && index++, ~index || (index = 0), $items.eq(index).focus()
                }
            }
        }
    };
    var old = $.fn.dropdown;
    $.fn.dropdown = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("dropdown");
            data || $this.data("dropdown", data = new Dropdown(this)), "string" == typeof option && data[option].call($this)
        })
    }, $.fn.dropdown.Constructor = Dropdown, $.fn.dropdown.noConflict = function () {
        return $.fn.dropdown = old, this
    }, $(document).on("click.bs.dropdown.data-api", clearMenus).on("click.bs.dropdown.data-api", ".dropdown form", function (e) {
        e.stopPropagation()
    }).on("click.bs.dropdown.data-api", toggle, Dropdown.prototype.toggle).on("keydown.bs.dropdown.data-api", toggle + ", [role=menu]", Dropdown.prototype.keydown)
}(jQuery), +function ($) {
    "use strict";
    var Modal = function (element, options) {
        this.options = options, this.$element = $(element), this.$backdrop = this.isShown = null, this.options.remote && this.$element.load(this.options.remote)
    };
    Modal.DEFAULTS = {backdrop: !0, keyboard: !0, show: !0}, Modal.prototype.toggle = function (_relatedTarget) {
        return this[this.isShown ? "hide" : "show"](_relatedTarget)
    }, Modal.prototype.show = function (_relatedTarget) {
        var that = this, e = $.Event("show.bs.modal", {relatedTarget: _relatedTarget});
        this.$element.trigger(e), this.isShown || e.isDefaultPrevented() || (this.isShown = !0, this.escape(), this.$element.on("click.dismiss.modal", '[data-dismiss="modal"]', $.proxy(this.hide, this)), this.backdrop(function () {
            var transition = $.support.transition && that.$element.hasClass("fade");
            that.$element.parent().length || that.$element.appendTo(document.body), that.$element.show(), transition && that.$element[0].offsetWidth, that.$element.addClass("in").attr("aria-hidden", !1), that.enforceFocus();
            var e = $.Event("shown.bs.modal", {relatedTarget: _relatedTarget});
            transition ? that.$element.find(".modal-dialog").one($.support.transition.end, function () {
                that.$element.focus().trigger(e)
            }).emulateTransitionEnd(300) : that.$element.focus().trigger(e)
        }))
    }, Modal.prototype.hide = function (e) {
        e && e.preventDefault(), e = $.Event("hide.bs.modal"), this.$element.trigger(e), this.isShown && !e.isDefaultPrevented() && (this.isShown = !1, this.escape(), $(document).off("focusin.bs.modal"), this.$element.removeClass("in").attr("aria-hidden", !0).off("click.dismiss.modal"), $.support.transition && this.$element.hasClass("fade") ? this.$element.one($.support.transition.end, $.proxy(this.hideModal, this)).emulateTransitionEnd(300) : this.hideModal())
    }, Modal.prototype.enforceFocus = function () {
        $(document).off("focusin.bs.modal").on("focusin.bs.modal", $.proxy(function (e) {
            this.$element[0] === e.target || this.$element.has(e.target).length || this.$element.focus()
        }, this))
    }, Modal.prototype.escape = function () {
        this.isShown && this.options.keyboard ? this.$element.on("keyup.dismiss.bs.modal", $.proxy(function (e) {
            27 == e.which && this.hide()
        }, this)) : this.isShown || this.$element.off("keyup.dismiss.bs.modal")
    }, Modal.prototype.hideModal = function () {
        var that = this;
        this.$element.hide(), this.backdrop(function () {
            that.removeBackdrop(), that.$element.trigger("hidden.bs.modal")
        })
    }, Modal.prototype.removeBackdrop = function () {
        this.$backdrop && this.$backdrop.remove(), this.$backdrop = null
    }, Modal.prototype.backdrop = function (callback) {
        var animate = this.$element.hasClass("fade") ? "fade" : "";
        if (this.isShown && this.options.backdrop) {
            var doAnimate = $.support.transition && animate;
            if (this.$backdrop = $('<div class="modal-backdrop ' + animate + '" />').appendTo(document.body), this.$element.on("click.dismiss.modal", $.proxy(function (e) {
                e.target === e.currentTarget && ("static" == this.options.backdrop ? this.$element[0].focus.call(this.$element[0]) : this.hide.call(this))
            }, this)), doAnimate && this.$backdrop[0].offsetWidth, this.$backdrop.addClass("in"), !callback) return;
            doAnimate ? this.$backdrop.one($.support.transition.end, callback).emulateTransitionEnd(150) : callback()
        } else !this.isShown && this.$backdrop ? (this.$backdrop.removeClass("in"), $.support.transition && this.$element.hasClass("fade") ? this.$backdrop.one($.support.transition.end, callback).emulateTransitionEnd(150) : callback()) : callback && callback()
    };
    var old = $.fn.modal;
    $.fn.modal = function (option, _relatedTarget) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.modal"),
                options = $.extend({}, Modal.DEFAULTS, $this.data(), "object" == typeof option && option);
            data || $this.data("bs.modal", data = new Modal(this, options)), "string" == typeof option ? data[option](_relatedTarget) : options.show && data.show(_relatedTarget)
        })
    }, $.fn.modal.Constructor = Modal, $.fn.modal.noConflict = function () {
        return $.fn.modal = old, this
    }, $(document).on("click.bs.modal.data-api", '[data-toggle="modal"]', function (e) {
        var $this = $(this), href = $this.attr("href"),
            $target = $($this.attr("data-target") || href && href.replace(/.*(?=#[^\s]+$)/, "")),
            option = $target.data("modal") ? "toggle" : $.extend({remote: !/#/.test(href) && href}, $target.data(), $this.data());
        e.preventDefault(), $target.modal(option, this).one("hide", function () {
            $this.is(":visible") && $this.focus()
        })
    }), $(document).on("show.bs.modal", ".modal", function () {
        $(document.body).addClass("modal-open")
    }).on("hidden.bs.modal", ".modal", function () {
        $(document.body).removeClass("modal-open")
    })
}(jQuery), +function ($) {
    "use strict";
    var Tooltip = function (element, options) {
        this.type = this.options = this.enabled = this.timeout = this.hoverState = this.$element = null, this.init("tooltip", element, options)
    };
    Tooltip.DEFAULTS = {
        animation: !0,
        placement: "top",
        selector: !1,
        template: '<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
        trigger: "hover focus",
        title: "",
        delay: 0,
        html: !1,
        container: !1
    }, Tooltip.prototype.init = function (type, element, options) {
        this.enabled = !0, this.type = type, this.$element = $(element), this.options = this.getOptions(options);
        for (var triggers = this.options.trigger.split(" "), i = triggers.length; i--;) {
            var trigger = triggers[i];
            if ("click" == trigger) this.$element.on("click." + this.type, this.options.selector, $.proxy(this.toggle, this)); else if ("manual" != trigger) {
                var eventIn = "hover" == trigger ? "mouseenter" : "focus",
                    eventOut = "hover" == trigger ? "mouseleave" : "blur";
                this.$element.on(eventIn + "." + this.type, this.options.selector, $.proxy(this.enter, this)), this.$element.on(eventOut + "." + this.type, this.options.selector, $.proxy(this.leave, this))
            }
        }
        this.options.selector ? this._options = $.extend({}, this.options, {
            trigger: "manual",
            selector: ""
        }) : this.fixTitle()
    }, Tooltip.prototype.getDefaults = function () {
        return Tooltip.DEFAULTS
    }, Tooltip.prototype.getOptions = function (options) {
        return options = $.extend({}, this.getDefaults(), this.$element.data(), options), options.delay && "number" == typeof options.delay && (options.delay = {
            show: options.delay,
            hide: options.delay
        }), options
    }, Tooltip.prototype.getDelegateOptions = function () {
        var options = {}, defaults = this.getDefaults();
        return this._options && $.each(this._options, function (key, value) {
            defaults[key] != value && (options[key] = value)
        }), options
    }, Tooltip.prototype.enter = function (obj) {
        var self = obj instanceof this.constructor ? obj : $(obj.currentTarget)[this.type](this.getDelegateOptions()).data("bs." + this.type);
        return clearTimeout(self.timeout), self.hoverState = "in", self.options.delay && self.options.delay.show ? (self.timeout = setTimeout(function () {
            "in" == self.hoverState && self.show()
        }, self.options.delay.show), void 0) : self.show()
    }, Tooltip.prototype.leave = function (obj) {
        var self = obj instanceof this.constructor ? obj : $(obj.currentTarget)[this.type](this.getDelegateOptions()).data("bs." + this.type);
        return clearTimeout(self.timeout), self.hoverState = "out", self.options.delay && self.options.delay.hide ? (self.timeout = setTimeout(function () {
            "out" == self.hoverState && self.hide()
        }, self.options.delay.hide), void 0) : self.hide()
    }, Tooltip.prototype.show = function () {
        var e = $.Event("show.bs." + this.type);
        if (this.hasContent() && this.enabled) {
            if (this.$element.trigger(e), e.isDefaultPrevented()) return;
            var $tip = this.tip();
            this.setContent(), this.options.animation && $tip.addClass("fade");
            var placement = "function" == typeof this.options.placement ? this.options.placement.call(this, $tip[0], this.$element[0]) : this.options.placement,
                autoToken = /\s?auto?\s?/i, autoPlace = autoToken.test(placement);
            autoPlace && (placement = placement.replace(autoToken, "") || "top"), $tip.detach().css({
                top: 0,
                left: 0,
                display: "block"
            }).addClass(placement), this.options.container ? $tip.appendTo(this.options.container) : $tip.insertAfter(this.$element);
            var pos = this.getPosition(), actualWidth = $tip[0].offsetWidth, actualHeight = $tip[0].offsetHeight;
            if (autoPlace) {
                var $parent = this.$element.parent(), orgPlacement = placement,
                    docScroll = document.documentElement.scrollTop || document.body.scrollTop,
                    parentWidth = "body" == this.options.container ? window.innerWidth : $parent.outerWidth(),
                    parentHeight = "body" == this.options.container ? window.innerHeight : $parent.outerHeight(),
                    parentLeft = "body" == this.options.container ? 0 : $parent.offset().left;
                placement = "bottom" == placement && pos.top + pos.height + actualHeight - docScroll > parentHeight ? "top" : "top" == placement && pos.top - docScroll - actualHeight < 0 ? "bottom" : "right" == placement && pos.right + actualWidth > parentWidth ? "left" : "left" == placement && pos.left - actualWidth < parentLeft ? "right" : placement, $tip.removeClass(orgPlacement).addClass(placement)
            }
            var calculatedOffset = this.getCalculatedOffset(placement, pos, actualWidth, actualHeight);
            this.applyPlacement(calculatedOffset, placement), this.$element.trigger("shown.bs." + this.type)
        }
    }, Tooltip.prototype.applyPlacement = function (offset, placement) {
        var replace, $tip = this.tip(), width = $tip[0].offsetWidth, height = $tip[0].offsetHeight,
            marginTop = parseInt($tip.css("margin-top"), 10), marginLeft = parseInt($tip.css("margin-left"), 10);
        isNaN(marginTop) && (marginTop = 0), isNaN(marginLeft) && (marginLeft = 0), offset.top = offset.top + marginTop, offset.left = offset.left + marginLeft, $tip.offset(offset).addClass("in");
        var actualWidth = $tip[0].offsetWidth, actualHeight = $tip[0].offsetHeight;
        if ("top" == placement && actualHeight != height && (replace = !0, offset.top = offset.top + height - actualHeight), /bottom|top/.test(placement)) {
            var delta = 0;
            offset.left < 0 && (delta = -2 * offset.left, offset.left = 0, $tip.offset(offset), actualWidth = $tip[0].offsetWidth, actualHeight = $tip[0].offsetHeight), this.replaceArrow(delta - width + actualWidth, actualWidth, "left")
        } else this.replaceArrow(actualHeight - height, actualHeight, "top");
        replace && $tip.offset(offset)
    }, Tooltip.prototype.replaceArrow = function (delta, dimension, position) {
        this.arrow().css(position, delta ? 50 * (1 - delta / dimension) + "%" : "")
    }, Tooltip.prototype.setContent = function () {
        var $tip = this.tip(), title = this.getTitle();
        $tip.find(".tooltip-inner")[this.options.html ? "html" : "text"](title), $tip.removeClass("fade in top bottom left right")
    }, Tooltip.prototype.hide = function () {
        function complete() {
            "in" != that.hoverState && $tip.detach()
        }

        var that = this, $tip = this.tip(), e = $.Event("hide.bs." + this.type);
        return this.$element.trigger(e), e.isDefaultPrevented() ? void 0 : ($tip.removeClass("in"), $.support.transition && this.$tip.hasClass("fade") ? $tip.one($.support.transition.end, complete).emulateTransitionEnd(150) : complete(), this.$element.trigger("hidden.bs." + this.type), this)
    }, Tooltip.prototype.fixTitle = function () {
        var $e = this.$element;
        ($e.attr("title") || "string" != typeof $e.attr("data-original-title")) && $e.attr("data-original-title", $e.attr("title") || "").attr("title", "")
    }, Tooltip.prototype.hasContent = function () {
        return this.getTitle()
    }, Tooltip.prototype.getPosition = function () {
        var el = this.$element[0];
        return $.extend({}, "function" == typeof el.getBoundingClientRect ? el.getBoundingClientRect() : {
            width: el.offsetWidth,
            height: el.offsetHeight
        }, this.$element.offset())
    }, Tooltip.prototype.getCalculatedOffset = function (placement, pos, actualWidth, actualHeight) {
        return "bottom" == placement ? {
            top: pos.top + pos.height,
            left: pos.left + pos.width / 2 - actualWidth / 2
        } : "top" == placement ? {
            top: pos.top - actualHeight,
            left: pos.left + pos.width / 2 - actualWidth / 2
        } : "left" == placement ? {
            top: pos.top + pos.height / 2 - actualHeight / 2,
            left: pos.left - actualWidth
        } : {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width}
    }, Tooltip.prototype.getTitle = function () {
        var title, $e = this.$element, o = this.options;
        return title = $e.attr("data-original-title") || ("function" == typeof o.title ? o.title.call($e[0]) : o.title)
    }, Tooltip.prototype.tip = function () {
        return this.$tip = this.$tip || $(this.options.template)
    }, Tooltip.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".tooltip-arrow")
    }, Tooltip.prototype.validate = function () {
        this.$element[0].parentNode || (this.hide(), this.$element = null, this.options = null)
    }, Tooltip.prototype.enable = function () {
        this.enabled = !0
    }, Tooltip.prototype.disable = function () {
        this.enabled = !1
    }, Tooltip.prototype.toggleEnabled = function () {
        this.enabled = !this.enabled
    }, Tooltip.prototype.toggle = function (e) {
        var self = e ? $(e.currentTarget)[this.type](this.getDelegateOptions()).data("bs." + this.type) : this;
        self.tip().hasClass("in") ? self.leave(self) : self.enter(self)
    }, Tooltip.prototype.destroy = function () {
        this.hide().$element.off("." + this.type).removeData("bs." + this.type)
    };
    var old = $.fn.tooltip;
    $.fn.tooltip = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.tooltip"), options = "object" == typeof option && option;
            data || $this.data("bs.tooltip", data = new Tooltip(this, options)), "string" == typeof option && data[option]()
        })
    }, $.fn.tooltip.Constructor = Tooltip, $.fn.tooltip.noConflict = function () {
        return $.fn.tooltip = old, this
    }
}(jQuery), +function ($) {
    "use strict";
    var Popover = function (element, options) {
        this.init("popover", element, options)
    };
    if (!$.fn.tooltip) throw new Error("Popover requires tooltip.js");
    Popover.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
        placement: "right",
        trigger: "click",
        content: "",
        template: '<div class="popover"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
    }), Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype), Popover.prototype.constructor = Popover, Popover.prototype.getDefaults = function () {
        return Popover.DEFAULTS
    }, Popover.prototype.setContent = function () {
        var $tip = this.tip(), title = this.getTitle(), content = this.getContent();
        $tip.find(".popover-title")[this.options.html ? "html" : "text"](title), $tip.find(".popover-content")[this.options.html ? "html" : "text"](content), $tip.removeClass("fade top bottom left right in"), $tip.find(".popover-title").html() || $tip.find(".popover-title").hide()
    }, Popover.prototype.hasContent = function () {
        return this.getTitle() || this.getContent()
    }, Popover.prototype.getContent = function () {
        var $e = this.$element, o = this.options;
        return $e.attr("data-content") || ("function" == typeof o.content ? o.content.call($e[0]) : o.content)
    }, Popover.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".arrow")
    }, Popover.prototype.tip = function () {
        return this.$tip || (this.$tip = $(this.options.template)), this.$tip
    };
    var old = $.fn.popover;
    $.fn.popover = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.popover"), options = "object" == typeof option && option;
            data || $this.data("bs.popover", data = new Popover(this, options)), "string" == typeof option && data[option]()
        })
    }, $.fn.popover.Constructor = Popover, $.fn.popover.noConflict = function () {
        return $.fn.popover = old, this
    }
}(jQuery), +function ($) {
    "use strict";

    function ScrollSpy(element, options) {
        var href, process = $.proxy(this.process, this);
        this.$element = $(element).is("body") ? $(window) : $(element), this.$body = $("body"), this.$scrollElement = this.$element.on("scroll.bs.scroll-spy.data-api", process), this.options = $.extend({}, ScrollSpy.DEFAULTS, options), this.selector = (this.options.target || (href = $(element).attr("href")) && href.replace(/.*(?=#[^\s]+$)/, "") || "") + " .nav li > a", this.offsets = $([]), this.targets = $([]), this.activeTarget = null, this.refresh(), this.process()
    }

    ScrollSpy.DEFAULTS = {offset: 10}, ScrollSpy.prototype.refresh = function () {
        var offsetMethod = this.$element[0] == window ? "offset" : "position";
        this.offsets = $([]), this.targets = $([]);
        {
            var self = this;
            this.$body.find(this.selector).map(function () {
                var $el = $(this), href = $el.data("target") || $el.attr("href"), $href = /^#\w/.test(href) && $(href);
                return $href && $href.length && [[$href[offsetMethod]().top + (!$.isWindow(self.$scrollElement.get(0)) && self.$scrollElement.scrollTop()), href]] || null
            }).sort(function (a, b) {
                return a[0] - b[0]
            }).each(function () {
                self.offsets.push(this[0]), self.targets.push(this[1])
            })
        }
    }, ScrollSpy.prototype.process = function () {
        var i, scrollTop = this.$scrollElement.scrollTop() + this.options.offset,
            scrollHeight = this.$scrollElement[0].scrollHeight || this.$body[0].scrollHeight,
            maxScroll = scrollHeight - this.$scrollElement.height(), offsets = this.offsets, targets = this.targets,
            activeTarget = this.activeTarget;
        if (scrollTop >= maxScroll) return activeTarget != (i = targets.last()[0]) && this.activate(i);
        for (i = offsets.length; i--;) activeTarget != targets[i] && scrollTop >= offsets[i] && (!offsets[i + 1] || scrollTop <= offsets[i + 1]) && this.activate(targets[i])
    }, ScrollSpy.prototype.activate = function (target) {
        this.activeTarget = target, $(this.selector).parents(".active").removeClass("active");
        var selector = this.selector + '[data-target="' + target + '"],' + this.selector + '[href="' + target + '"]',
            active = $(selector).parents("li").addClass("active");
        active.parent(".dropdown-menu").length && (active = active.closest("li.dropdown").addClass("active")), active.trigger("activate")
    };
    var old = $.fn.scrollspy;
    $.fn.scrollspy = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.scrollspy"), options = "object" == typeof option && option;
            data || $this.data("bs.scrollspy", data = new ScrollSpy(this, options)), "string" == typeof option && data[option]()
        })
    }, $.fn.scrollspy.Constructor = ScrollSpy, $.fn.scrollspy.noConflict = function () {
        return $.fn.scrollspy = old, this
    }, $(window).on("load", function () {
        $('[data-spy="scroll"]').each(function () {
            var $spy = $(this);
            $spy.scrollspy($spy.data())
        })
    })
}(jQuery), +function ($) {
    "use strict";
    var Tab = function (element) {
        this.element = $(element)
    };
    Tab.prototype.show = function () {
        var $this = this.element, $ul = $this.closest("ul:not(.dropdown-menu)"), selector = $this.data("target");
        if (selector || (selector = $this.attr("href"), selector = selector && selector.replace(/.*(?=#[^\s]*$)/, "")), !$this.parent("li").hasClass("active")) {
            var previous = $ul.find(".active:last a")[0], e = $.Event("show.bs.tab", {relatedTarget: previous});
            if ($this.trigger(e), !e.isDefaultPrevented()) {
                var $target = $(selector);
                this.activate($this.parent("li"), $ul), this.activate($target, $target.parent(), function () {
                    $this.trigger({type: "shown.bs.tab", relatedTarget: previous})
                })
            }
        }
    }, Tab.prototype.activate = function (element, container, callback) {
        function next() {
            $active.removeClass("active").find("> .dropdown-menu > .active").removeClass("active"), element.addClass("active"), transition ? (element[0].offsetWidth, element.addClass("in")) : element.removeClass("fade"), element.parent(".dropdown-menu") && element.closest("li.dropdown").addClass("active"), callback && callback()
        }

        var $active = container.find("> .active"),
            transition = callback && $.support.transition && $active.hasClass("fade");
        transition ? $active.one($.support.transition.end, next).emulateTransitionEnd(150) : next(), $active.removeClass("in")
    };
    var old = $.fn.tab;
    $.fn.tab = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.tab");
            data || $this.data("bs.tab", data = new Tab(this)), "string" == typeof option && data[option]()
        })
    }, $.fn.tab.Constructor = Tab, $.fn.tab.noConflict = function () {
        return $.fn.tab = old, this
    }, $(document).on("click.bs.tab.data-api", '[data-toggle="tab"], [data-toggle="pill"]', function (e) {
        e.preventDefault(), $(this).tab("show")
    })
}(jQuery), +function ($) {
    "use strict";
    var Affix = function (element, options) {
        this.options = $.extend({}, Affix.DEFAULTS, options), this.$window = $(window).on("scroll.bs.affix.data-api", $.proxy(this.checkPosition, this)).on("click.bs.affix.data-api", $.proxy(this.checkPositionWithEventLoop, this)), this.$element = $(element), this.affixed = this.unpin = null, this.checkPosition()
    };
    Affix.RESET = "affix affix-top affix-bottom", Affix.DEFAULTS = {offset: 0}, Affix.prototype.checkPositionWithEventLoop = function () {
        setTimeout($.proxy(this.checkPosition, this), 1)
    }, Affix.prototype.checkPosition = function () {
        if (this.$element.is(":visible")) {
            var scrollHeight = $(document).height(), scrollTop = this.$window.scrollTop(),
                position = this.$element.offset(), offset = this.options.offset, offsetTop = offset.top,
                offsetBottom = offset.bottom;
            "object" != typeof offset && (offsetBottom = offsetTop = offset), "function" == typeof offsetTop && (offsetTop = offset.top()), "function" == typeof offsetBottom && (offsetBottom = offset.bottom());
            var affix = null != this.unpin && scrollTop + this.unpin <= position.top ? !1 : null != offsetBottom && position.top + this.$element.height() >= scrollHeight - offsetBottom ? "bottom" : null != offsetTop && offsetTop >= scrollTop ? "top" : !1;
            this.affixed !== affix && (this.unpin && this.$element.css("top", ""), this.affixed = affix, this.unpin = "bottom" == affix ? position.top - scrollTop : null, this.$element.removeClass(Affix.RESET).addClass("affix" + (affix ? "-" + affix : "")), "bottom" == affix && this.$element.offset({top: document.body.offsetHeight - offsetBottom - this.$element.height()}))
        }
    };
    var old = $.fn.affix;
    $.fn.affix = function (option) {
        return this.each(function () {
            var $this = $(this), data = $this.data("bs.affix"), options = "object" == typeof option && option;
            data || $this.data("bs.affix", data = new Affix(this, options)), "string" == typeof option && data[option]()
        })
    }, $.fn.affix.Constructor = Affix, $.fn.affix.noConflict = function () {
        return $.fn.affix = old, this
    }, $(window).on("load", function () {
        $('[data-spy="affix"]').each(function () {
            var $spy = $(this), data = $spy.data();
            data.offset = data.offset || {}, data.offsetBottom && (data.offset.bottom = data.offsetBottom), data.offsetTop && (data.offset.top = data.offsetTop), $spy.affix(data)
        })
    })
}(jQuery);