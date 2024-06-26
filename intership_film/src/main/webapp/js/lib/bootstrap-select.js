!function ($) {
    "use strict";
    $.expr[":"].icontains = function (obj, index, meta) {
        return $(obj).text().toUpperCase().indexOf(meta[3].toUpperCase()) >= 0
    };
    var Selectpicker = function (element, options, e) {
        e && (e.stopPropagation(), e.preventDefault()), this.$element = $(element), this.$newElement = null, this.$button = null, this.$menu = null, this.$lis = null, this.options = $.extend({}, $.fn.selectpicker.defaults, this.$element.data(), "object" == typeof options && options), null === this.options.title && (this.options.title = this.$element.attr("title")), this.val = Selectpicker.prototype.val, this.render = Selectpicker.prototype.render, this.refresh = Selectpicker.prototype.refresh, this.setStyle = Selectpicker.prototype.setStyle, this.selectAll = Selectpicker.prototype.selectAll, this.deselectAll = Selectpicker.prototype.deselectAll, this.init()
    };
    Selectpicker.prototype = {
        constructor: Selectpicker, init: function () {
            var that = this, id = this.$element.attr("id");
            this.$element.hide(), this.multiple = this.$element.prop("multiple"), this.autofocus = this.$element.prop("autofocus"), this.$newElement = this.createView(), this.$element.after(this.$newElement), this.$menu = this.$newElement.find("> .dropdown-menu"), this.$button = this.$newElement.find("> button"), this.$searchbox = this.$newElement.find("input"), void 0 !== id && (this.$button.attr("data-id", id), $('label[for="' + id + '"]').click(function (e) {
                e.preventDefault(), that.$button.focus()
            })), this.checkDisabled(), this.clickListener(), this.options.liveSearch && this.liveSearchListener(), this.render(), this.liHeight(), this.setStyle(), this.setWidth(), this.options.container && this.selectPosition(), this.$menu.data("this", this), this.$newElement.data("this", this)
        }, createDropdown: function () {
            var multiple = this.multiple ? " show-tick" : "", autofocus = this.autofocus ? " autofocus" : "",
                header = this.options.header ? '<div class="popover-title"><button type="button" class="close" aria-hidden="true">&times;</button>' + this.options.header + "</div>" : "",
                searchbox = this.options.liveSearch ? '<div class="bootstrap-select-searchbox"><input type="text" class="input-block-level form-control" /></div>' : "",
                drop = '<div class="btn-group bootstrap-select' + multiple + '"><button type="button" class="btn dropdown-toggle selectpicker" data-toggle="dropdown"' + autofocus + '><span class="filter-option pull-left"></span>&nbsp;<span class="caret"></span></button><div class="dropdown-menu open">' + header + searchbox + '<ul class="dropdown-menu inner selectpicker" role="menu"></ul></div></div>';
            return $(drop)
        }, createView: function () {
            var $drop = this.createDropdown(), $li = this.createLi();
            return $drop.find("ul").append($li), $drop
        }, reloadLi: function () {
            this.destroyLi();
            var $li = this.createLi();
            this.$menu.find("ul").append($li)
        }, destroyLi: function () {
            this.$menu.find("li").remove()
        }, createLi: function () {
            var that = this, _liA = [], _liHtml = "";
            return this.$element.find("option").each(function () {
                var $this = $(this), optionClass = $this.attr("class") || "", inline = $this.attr("style") || "",
                    text = $this.data("content") ? $this.data("content") : $this.html(),
                    subtext = void 0 !== $this.data("subtext") ? '<small class="muted text-muted">' + $this.data("subtext") + "</small>" : "",
                    icon = void 0 !== $this.data("icon") ? '<i class="' + that.options.iconBase + " " + $this.data("icon") + '"></i> ' : "";
                if ("" !== icon && ($this.is(":disabled") || $this.parent().is(":disabled")) && (icon = "<span>" + icon + "</span>"), $this.data("content") || (text = icon + '<span class="text">' + text + subtext + "</span>"), that.options.hideDisabled && ($this.is(":disabled") || $this.parent().is(":disabled"))) _liA.push('<a style="min-height: 0; padding: 0"></a>'); else if ($this.parent().is("optgroup") && $this.data("divider") !== !0) if (0 === $this.index()) {
                    var label = $this.parent().attr("label"),
                        labelSubtext = void 0 !== $this.parent().data("subtext") ? '<small class="muted text-muted">' + $this.parent().data("subtext") + "</small>" : "",
                        labelIcon = $this.parent().data("icon") ? '<i class="' + $this.parent().data("icon") + '"></i> ' : "";
                    label = labelIcon + '<span class="text">' + label + labelSubtext + "</span>", 0 !== $this[0].index ? _liA.push('<div class="div-contain"><div class="divider"></div></div><dt>' + label + "</dt>" + that.createA(text, "opt " + optionClass, inline)) : _liA.push("<dt>" + label + "</dt>" + that.createA(text, "opt " + optionClass, inline))
                } else _liA.push(that.createA(text, "opt " + optionClass, inline)); else $this.data("divider") === !0 ? _liA.push('<div class="div-contain"><div class="divider"></div></div>') : $(this).data("hidden") === !0 ? _liA.push("") : _liA.push(that.createA(text, optionClass, inline))
            }), $.each(_liA, function (i, item) {
                _liHtml += "<li rel=" + i + ">" + item + "</li>"
            }), this.multiple || 0 !== this.$element.find("option:selected").length || this.options.title || this.$element.find("option").eq(0).prop("selected", !0).attr("selected", "selected"), $(_liHtml)
        }, createA: function (text, classes, inline) {
            return '<a tabindex="0" class="' + classes + '" style="' + inline + '">' + text + '<i class="' + this.options.iconBase + " " + this.options.tickIcon + ' icon-ok check-mark"></i></a>'
        }, render: function (updateLi) {
            var that = this;
            updateLi !== !1 && this.$element.find("option").each(function (index) {
                that.setDisabled(index, $(this).is(":disabled") || $(this).parent().is(":disabled")), that.setSelected(index, $(this).is(":selected"))
            }), this.tabIndex();
            var selectedItems = this.$element.find("option:selected").map(function () {
                var subtext, $this = $(this),
                    icon = $this.data("icon") && that.options.showIcon ? '<i class="' + that.options.iconBase + " " + $this.data("icon") + '"></i> ' : "";
                return subtext = that.options.showSubtext && $this.attr("data-subtext") && !that.multiple ? ' <small class="muted text-muted">' + $this.data("subtext") + "</small>" : "", $this.data("content") && that.options.showContent ? $this.data("content") : void 0 !== $this.attr("title") ? $this.attr("title") : icon + $this.html() + subtext
            }).toArray(), title = this.multiple ? selectedItems.join(this.options.multipleSeparator) : selectedItems[0];
            if (this.multiple && this.options.selectedTextFormat.indexOf("count") > -1) {
                var max = this.options.selectedTextFormat.split(">"),
                    notDisabled = this.options.hideDisabled ? ":not([disabled])" : "";
                (max.length > 1 && selectedItems.length > max[1] || 1 == max.length && selectedItems.length >= 2) && (title = this.options.countSelectedText.replace("{0}", selectedItems.length).replace("{1}", this.$element.find('option:not([data-divider="true"]):not([data-hidden="true"])' + notDisabled).length))
            }
            title || (title = void 0 !== this.options.title ? this.options.title : this.options.noneSelectedText), this.$button.attr("title", $.trim(title)), this.$newElement.find(".filter-option").html(title)
        }, setStyle: function (style, status) {
            this.$element.attr("class") && this.$newElement.addClass(this.$element.attr("class").replace(/selectpicker|mobile-device/gi, ""));
            var buttonClass = style ? style : this.options.style;
            "add" == status ? this.$button.addClass(buttonClass) : "remove" == status ? this.$button.removeClass(buttonClass) : (this.$button.removeClass(this.options.style), this.$button.addClass(buttonClass))
        }, liHeight: function () {
            var $selectClone = this.$menu.parent().clone().find("> .dropdown-toggle").prop("autofocus", !1).end().appendTo("body"),
                $menuClone = $selectClone.addClass("open").find("> .dropdown-menu"),
                liHeight = $menuClone.find("li > a").outerHeight(),
                headerHeight = this.options.header ? $menuClone.find(".popover-title").outerHeight() : 0,
                searchHeight = this.options.liveSearch ? $menuClone.find(".bootstrap-select-searchbox").outerHeight() : 0;
            $selectClone.remove(), this.$newElement.data("liHeight", liHeight).data("headerHeight", headerHeight).data("searchHeight", searchHeight)
        }, setSize: function () {
            var menuHeight, selectOffsetTop, selectOffsetBot, that = this, menu = this.$menu,
                menuInner = menu.find(".inner"), selectHeight = this.$newElement.outerHeight(),
                liHeight = this.$newElement.data("liHeight"), headerHeight = this.$newElement.data("headerHeight"),
                searchHeight = this.$newElement.data("searchHeight"),
                divHeight = menu.find("li .divider").outerHeight(!0),
                menuPadding = parseInt(menu.css("padding-top")) + parseInt(menu.css("padding-bottom")) + parseInt(menu.css("border-top-width")) + parseInt(menu.css("border-bottom-width")),
                notDisabled = this.options.hideDisabled ? ":not(.disabled)" : "", $window = $(window),
                menuExtras = menuPadding + parseInt(menu.css("margin-top")) + parseInt(menu.css("margin-bottom")) + 2,
                posVert = function () {
                    selectOffsetTop = that.$newElement.offset().top - $window.scrollTop(), selectOffsetBot = $window.height() - selectOffsetTop - selectHeight
                };
            if (posVert(), this.options.header && menu.css("padding-top", 0), "auto" == this.options.size) {
                var getSize = function () {
                    var minHeight;
                    posVert(), menuHeight = selectOffsetBot - menuExtras, that.options.dropupAuto && that.$newElement.toggleClass("dropup", selectOffsetTop > selectOffsetBot && menuHeight - menuExtras < menu.height()), that.$newElement.hasClass("dropup") && (menuHeight = selectOffsetTop - menuExtras), minHeight = menu.find("li").length + menu.find("dt").length > 3 ? 3 * liHeight + menuExtras - 2 : 0, menu.css({
                        "max-height": menuHeight + "px",
                        overflow: "hidden",
                        "min-height": minHeight + "px"
                    }), menuInner.css({
                        "max-height": menuHeight - headerHeight - searchHeight - menuPadding + "px",
                        "overflow-y": "auto",
                        "min-height": minHeight - menuPadding + "px"
                    })
                };
                getSize(), $(window).resize(getSize), $(window).scroll(getSize)
            } else if (this.options.size && "auto" != this.options.size && menu.find("li" + notDisabled).length > this.options.size) {
                var optIndex = menu.find("li" + notDisabled + " > *").filter(":not(.div-contain)").slice(0, this.options.size).last().parent().index(),
                    divLength = menu.find("li").slice(0, optIndex + 1).find(".div-contain").length;
                menuHeight = liHeight * this.options.size + divLength * divHeight + menuPadding, that.options.dropupAuto && this.$newElement.toggleClass("dropup", selectOffsetTop > selectOffsetBot && menuHeight < menu.height()), menu.css({
                    "max-height": menuHeight + headerHeight + searchHeight + "px",
                    overflow: "hidden"
                }), menuInner.css({"max-height": menuHeight - menuPadding + "px", "overflow-y": "auto"})
            }
        }, setWidth: function () {
            if ("auto" == this.options.width) {
                this.$menu.css("min-width", "0");
                var selectClone = this.$newElement.clone().appendTo("body"),
                    ulWidth = selectClone.find("> .dropdown-menu").css("width");
                selectClone.remove(), this.$newElement.css("width", ulWidth)
            } else "fit" == this.options.width ? (this.$menu.css("min-width", ""), this.$newElement.css("width", "").addClass("fit-width")) : this.options.width ? (this.$menu.css("min-width", ""), this.$newElement.css("width", this.options.width)) : (this.$menu.css("min-width", ""), this.$newElement.css("width", ""));
            this.$newElement.hasClass("fit-width") && "fit" !== this.options.width && this.$newElement.removeClass("fit-width")
        }, selectPosition: function () {
            var pos, actualHeight, that = this, drop = "<div />", $drop = $(drop), getPlacement = function ($element) {
                $drop.addClass($element.attr("class")).toggleClass("dropup", $element.hasClass("dropup")), pos = $element.offset(), actualHeight = $element.hasClass("dropup") ? 0 : $element[0].offsetHeight, $drop.css({
                    top: pos.top + actualHeight,
                    left: pos.left,
                    width: $element[0].offsetWidth,
                    position: "absolute"
                })
            };
            this.$newElement.on("click", function () {
                getPlacement($(this)), $drop.appendTo(that.options.container), $drop.toggleClass("open", !$(this).hasClass("open")), $drop.append(that.$menu)
            }), $(window).resize(function () {
                getPlacement(that.$newElement)
            }), $(window).on("scroll", function () {
                getPlacement(that.$newElement)
            }), $("html").on("click", function (e) {
                $(e.target).closest(that.$newElement).length < 1 && $drop.removeClass("open")
            })
        }, mobile: function () {
            this.$element.addClass("mobile-device").appendTo(this.$newElement), this.options.container && this.$menu.hide()
        }, refresh: function () {
            this.$lis = null, this.reloadLi(), this.render(), this.setWidth(), this.setStyle(), this.checkDisabled(), this.liHeight()
        }, update: function () {
            this.reloadLi(), this.setWidth(), this.setStyle(), this.checkDisabled(), this.liHeight()
        }, setSelected: function (index, selected) {
            null == this.$lis && (this.$lis = this.$menu.find("li")), $(this.$lis[index]).toggleClass("selected", selected)
        }, setDisabled: function (index, disabled) {
            null == this.$lis && (this.$lis = this.$menu.find("li")), disabled ? $(this.$lis[index]).addClass("disabled").find("a").attr("href", "#").attr("tabindex", -1) : $(this.$lis[index]).removeClass("disabled").find("a").removeAttr("href").attr("tabindex", 0)
        }, isDisabled: function () {
            return this.$element.is(":disabled")
        }, checkDisabled: function () {
            var that = this;
            this.isDisabled() ? this.$button.addClass("disabled").attr("tabindex", -1) : (this.$button.hasClass("disabled") && this.$button.removeClass("disabled"), -1 == this.$button.attr("tabindex") && (this.$element.data("tabindex") || this.$button.removeAttr("tabindex"))), this.$button.click(function () {
                return !that.isDisabled()
            })
        }, tabIndex: function () {
            this.$element.is("[tabindex]") && (this.$element.data("tabindex", this.$element.attr("tabindex")), this.$button.attr("tabindex", this.$element.data("tabindex")))
        }, clickListener: function () {
            var that = this;
            $("body").on("touchstart.dropdown", ".dropdown-menu", function (e) {
                e.stopPropagation()
            }), this.$newElement.on("click", function () {
                that.setSize(), that.options.liveSearch || that.multiple || setTimeout(function () {
                    that.$menu.find(".selected a").focus()
                }, 10)
            }), this.$menu.on("click", "li a", function (e) {
                var clickedIndex = $(this).parent().index(), prevValue = that.$element.val(),
                    prevIndex = that.$element.prop("selectedIndex");
                if (that.multiple && e.stopPropagation(), e.preventDefault(), !that.isDisabled() && !$(this).parent().hasClass("disabled")) {
                    var $options = that.$element.find("option"), $option = $options.eq(clickedIndex),
                        state = $option.prop("selected");
                    that.multiple ? ($option.prop("selected", !state), that.setSelected(clickedIndex, !state)) : ($options.prop("selected", !1), $option.prop("selected", !0), that.$menu.find(".selected").removeClass("selected"), that.setSelected(clickedIndex, !0)), that.multiple ? that.options.liveSearch && that.$searchbox.focus() : that.$button.focus(), (prevValue != that.$element.val() && that.multiple || prevIndex != that.$element.prop("selectedIndex") && !that.multiple) && that.$element.change()
                }
            }), this.$menu.on("click", "li.disabled a, li dt, li .div-contain, .popover-title, .popover-title :not(.close)", function (e) {
                e.target == this && (e.preventDefault(), e.stopPropagation(), that.options.liveSearch ? that.$searchbox.focus() : that.$button.focus())
            }), this.$menu.on("click", ".popover-title .close", function () {
                that.$button.focus()
            }), this.$searchbox.on("click", function (e) {
                e.stopPropagation()
            }), this.$element.change(function () {
                that.render(!1)
            })
        }, liveSearchListener: function () {
            var that = this, no_results = $('<li class="no-results"></li>');
            this.$newElement.on("click.dropdown.data-api", function () {
                that.$menu.find(".active").removeClass("active"), that.$searchbox.val() && (that.$searchbox.val(""), that.$menu.find("li").show(), no_results.parent().length && no_results.remove()), that.multiple || that.$menu.find(".selected").addClass("active"), setTimeout(function () {
                    that.$searchbox.focus()
                }, 10)
            }), this.$searchbox.on("input propertychange", function () {
                that.$searchbox.val() ? (that.$menu.find("li").show().not(":icontains(" + that.$searchbox.val() + ")").hide(), that.$menu.find("li").filter(":visible:not(.no-results)").length ? no_results.parent().length && no_results.remove() : (no_results.parent().length && no_results.remove(), no_results.html(that.options.noneResultsText + ' "' + that.$searchbox.val() + '"').show(), that.$menu.find("li").last().after(no_results))) : (that.$menu.find("li").show(), no_results.parent().length && no_results.remove()), that.$menu.find("li.active").removeClass("active"), that.$menu.find("li").filter(":visible:not(.divider)").eq(0).addClass("active").find("a").focus(), $(this).focus()
            }), this.$menu.on("mouseenter", "a", function (e) {
                that.$menu.find(".active").removeClass("active"), $(e.currentTarget).parent().not(".disabled").addClass("active")
            }), this.$menu.on("mouseleave", "a", function () {
                that.$menu.find(".active").removeClass("active")
            })
        }, val: function (value) {
            return void 0 !== value ? (this.$element.val(value), this.$element.change(), this.$element) : this.$element.val()
        }, selectAll: function () {
            this.$element.find("option").prop("selected", !0).attr("selected", "selected"), this.render()
        }, deselectAll: function () {
            this.$element.find("option").prop("selected", !1).removeAttr("selected"), this.render()
        }, keydown: function (e) {
            var $this, $items, $parent, index, next, first, last, prev, nextPrev, that, prevIndex, isActive,
                keyCodeMap = {
                    32: " ",
                    48: "0",
                    49: "1",
                    50: "2",
                    51: "3",
                    52: "4",
                    53: "5",
                    54: "6",
                    55: "7",
                    56: "8",
                    57: "9",
                    59: ";",
                    65: "a",
                    66: "b",
                    67: "c",
                    68: "d",
                    69: "e",
                    70: "f",
                    71: "g",
                    72: "h",
                    73: "i",
                    74: "j",
                    75: "k",
                    76: "l",
                    77: "m",
                    78: "n",
                    79: "o",
                    80: "p",
                    81: "q",
                    82: "r",
                    83: "s",
                    84: "t",
                    85: "u",
                    86: "v",
                    87: "w",
                    88: "x",
                    89: "y",
                    90: "z",
                    96: "0",
                    97: "1",
                    98: "2",
                    99: "3",
                    100: "4",
                    101: "5",
                    102: "6",
                    103: "7",
                    104: "8",
                    105: "9"
                };
            if ($this = $(this), $parent = $this.parent(), $this.is("input") && ($parent = $this.parent().parent()), that = $parent.data("this"), that.options.liveSearch && ($parent = $this.parent().parent()), that.options.container && ($parent = that.$menu), $items = $("[role=menu] li:not(.divider) a", $parent), isActive = that.$menu.parent().hasClass("open"), isActive || /^9$/.test(e.keyCode) || (that.$menu.parent().addClass("open"), isActive = that.$menu.parent().hasClass("open"), that.$searchbox.focus()), that.options.liveSearch && (/(^9$|27)/.test(e.keyCode) && isActive && 0 === that.$menu.find(".active").length && (e.preventDefault(), that.$menu.parent().removeClass("open"), that.$button.focus()), $items = $("[role=menu] li:not(.divider):visible", $parent), $this.val() || /(38|40)/.test(e.keyCode) || 0 === $items.filter(".active").length && ($items = that.$newElement.find("li").filter(":icontains(" + keyCodeMap[e.keyCode] + ")"))), $items.length) {
                if (/(38|40)/.test(e.keyCode)) index = $items.index($items.filter(":focus")), first = $items.parent(":not(.disabled):visible").first().index(), last = $items.parent(":not(.disabled):visible").last().index(), next = $items.eq(index).parent().nextAll(":not(.disabled):visible").eq(0).index(), prev = $items.eq(index).parent().prevAll(":not(.disabled):visible").eq(0).index(), nextPrev = $items.eq(next).parent().prevAll(":not(.disabled):visible").eq(0).index(), that.options.liveSearch && ($items.each(function (i) {
                    $(this).is(":not(.disabled)") && $(this).data("index", i)
                }), index = $items.index($items.filter(".active")), first = $items.filter(":not(.disabled):visible").first().data("index"), last = $items.filter(":not(.disabled):visible").last().data("index"), next = $items.eq(index).nextAll(":not(.disabled):visible").eq(0).data("index"), prev = $items.eq(index).prevAll(":not(.disabled):visible").eq(0).data("index"), nextPrev = $items.eq(next).prevAll(":not(.disabled):visible").eq(0).data("index")), prevIndex = $this.data("prevIndex"), 38 == e.keyCode && (that.options.liveSearch && (index -= 1), index != nextPrev && index > prev && (index = prev), first > index && (index = first), index == prevIndex && (index = last)), 40 == e.keyCode && (that.options.liveSearch && (index += 1), -1 == index && (index = 0), index != nextPrev && next > index && (index = next), index > last && (index = last), index == prevIndex && (index = first)), $this.data("prevIndex", index), that.options.liveSearch ? (e.preventDefault(), $this.is(".dropdown-toggle") || ($items.removeClass("active"), $items.eq(index).addClass("active").find("a").focus(), $this.focus())) : $items.eq(index).focus(); else if (!$this.is("input")) {
                    var count, prevKey, keyIndex = [];
                    $items.each(function () {
                        $(this).parent().is(":not(.disabled)") && $.trim($(this).text().toLowerCase()).substring(0, 1) == keyCodeMap[e.keyCode] && keyIndex.push($(this).parent().index())
                    }), count = $(document).data("keycount"), count++, $(document).data("keycount", count), prevKey = $.trim($(":focus").text().toLowerCase()).substring(0, 1), prevKey != keyCodeMap[e.keyCode] ? (count = 1, $(document).data("keycount", count)) : count >= keyIndex.length && ($(document).data("keycount", 0), count > keyIndex.length && (count = 1)), $items.eq(keyIndex[count - 1]).focus()
                }
                /(13|32|^9$)/.test(e.keyCode) && isActive && (/(32)/.test(e.keyCode) || e.preventDefault(), that.options.liveSearch ? /(32)/.test(e.keyCode) || (that.$menu.find(".active a").click(), $this.focus()) : $(":focus").click(), $(document).data("keycount", 0)), (/(^9$|27)/.test(e.keyCode) && isActive && (that.multiple || that.options.liveSearch) || /(27)/.test(e.keyCode) && !isActive) && (that.$menu.parent().removeClass("open"), that.$button.focus())
            }
        }, hide: function () {
            this.$newElement.hide()
        }, show: function () {
            this.$newElement.show()
        }, destroy: function () {
            this.$newElement.remove(), this.$element.remove()
        }
    }, $.fn.selectpicker = function (option, event) {
        var value, args = arguments, chain = this.each(function () {
            if ($(this).is("select")) {
                var $this = $(this), data = $this.data("selectpicker"), options = "object" == typeof option && option;
                if (data) {
                    if (options) for (var i in options) data.options[i] = options[i]
                } else $this.data("selectpicker", data = new Selectpicker(this, options, event));
                if ("string" == typeof option) {
                    var property = option;
                    data[property] instanceof Function ? ([].shift.apply(args), value = data[property].apply(data, args)) : value = data.options[property]
                }
            }
        });
        return void 0 !== value ? value : chain
    }, $.fn.selectpicker.defaults = {
        style: "btn-default",
        size: "auto",
        title: null,
        selectedTextFormat: "values",
        noneSelectedText: "Nothing selected",
        noneResultsText: "No results match",
        countSelectedText: "{0} of {1} selected",
        width: !1,
        container: !1,
        hideDisabled: !1,
        showSubtext: !1,
        showIcon: !0,
        showContent: !0,
        dropupAuto: !0,
        header: !1,
        liveSearch: !1,
        multipleSeparator: ", ",
        iconBase: "glyphicon",
        tickIcon: "glyphicon-ok"
    }, $(document).data("keycount", 0).on("keydown", ".bootstrap-select [data-toggle=dropdown], .bootstrap-select [role=menu], .bootstrap-select-searchbox input", Selectpicker.prototype.keydown).on("focusin.modal", ".bootstrap-select [data-toggle=dropdown], .bootstrap-select [role=menu], .bootstrap-select-searchbox input", function (e) {
        e.stopPropagation()
    })
}(window.jQuery);