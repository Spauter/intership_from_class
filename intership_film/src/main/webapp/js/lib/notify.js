!function (window, document, $, undefined) {
    "use strict";
    var Notification, addStyle, blankFieldName, coreStyle, createElem, defaults, encode, find, findFields,
        getAnchorElement, getStyle, globalAnchors, hAligns, incr, inherit, insertCSS, mainPositions, opposites,
        parsePosition, pluginClassName, pluginName, pluginOptions, positions, realign, stylePrefixes, styles, vAligns,
        __indexOf = [].indexOf || function (item) {
            for (var i = 0, l = this.length; l > i; i++) if (i in this && this[i] === item) return i;
            return -1
        };
    pluginName = "notify", pluginClassName = pluginName + "js", blankFieldName = pluginName + "!blank", positions = {
        t: "top",
        m: "middle",
        b: "bottom",
        l: "left",
        c: "center",
        r: "right"
    }, hAligns = ["l", "c", "r"], vAligns = ["t", "m", "b"], mainPositions = ["t", "b", "l", "r"], opposites = {
        t: "b",
        m: null,
        b: "t",
        l: "r",
        c: null,
        r: "l"
    }, parsePosition = function (str) {
        var pos;
        return pos = [], $.each(str.split(/\W+/), function (i, word) {
            var w;
            return w = word.toLowerCase().charAt(0), positions[w] ? pos.push(w) : void 0
        }), pos
    }, styles = {}, coreStyle = {
        name: "core",
        html: '<div class="' + pluginClassName + '-wrapper">\n  <div class="' + pluginClassName + '-arrow"></div>\n  <div class="' + pluginClassName + '-container"></div>\n</div>',
        css: "." + pluginClassName + "-corner {\n  position: fixed;\n  margin: 5px;\n  z-index: 1050;\n}\n\n." + pluginClassName + "-corner ." + pluginClassName + "-wrapper,\n." + pluginClassName + "-corner ." + pluginClassName + "-container {\n  position: relative;\n  display: block;\n  height: inherit;\n  width: inherit;\n  margin: 3px;\n}\n\n." + pluginClassName + "-wrapper {\n  z-index: 1;\n  position: absolute;\n  display: inline-block;\n  height: 0;\n  width: 0;\n}\n\n." + pluginClassName + "-container {\n  display: none;\n  z-index: 1;\n  position: absolute;\n  cursor: pointer;\n}\n\n[data-notify-text],[data-notify-html] {\n  position: relative;\n}\n\n." + pluginClassName + "-arrow {\n  position: absolute;\n  z-index: 2;\n  width: 0;\n  height: 0;\n}"
    }, stylePrefixes = {"border-radius": ["-webkit-", "-moz-"]}, getStyle = function (name) {
        return styles[name]
    }, addStyle = function (name, def) {
        var cssText, elem, fields, _ref;
        if (!name) throw"Missing Style name";
        if (!def) throw"Missing Style definition";
        if (!def.html) throw"Missing Style HTML";
        return (null != (_ref = styles[name]) ? _ref.cssElem : void 0) && (window.console && console.warn("" + pluginName + ": overwriting style '" + name + "'"), styles[name].cssElem.remove()), def.name = name, styles[name] = def, cssText = "", def.classes && $.each(def.classes, function (className, props) {
            return cssText += "." + pluginClassName + "-" + def.name + "-" + className + " {\n", $.each(props, function (name, val) {
                return stylePrefixes[name] && $.each(stylePrefixes[name], function (i, prefix) {
                    return cssText += "  " + prefix + name + ": " + val + ";\n"
                }), cssText += "  " + name + ": " + val + ";\n"
            }), cssText += "}\n"
        }), def.css && (cssText += "/* styles for " + def.name + " */\n" + def.css), cssText && (def.cssElem = insertCSS(cssText), def.cssElem.attr("id", "notify-" + def.name)), fields = {}, elem = $(def.html), findFields("html", elem, fields), findFields("text", elem, fields), def.fields = fields
    }, insertCSS = function (cssText) {
        var elem;
        elem = createElem("style"), elem.attr("type", "text/css"), $("head").append(elem);
        try {
            elem.html(cssText)
        } catch (e) {
            elem[0].styleSheet.cssText = cssText
        }
        return elem
    }, findFields = function (type, elem, fields) {
        var attr;
        return "html" !== type && (type = "text"), attr = "data-notify-" + type, find(elem, "[" + attr + "]").each(function () {
            var name;
            return name = $(this).attr(attr), name || (name = blankFieldName), fields[name] = type
        })
    }, find = function (elem, selector) {
        return elem.is(selector) ? elem : elem.find(selector)
    }, pluginOptions = {
        clickToHide: !0,
        autoHide: !0,
        autoHideDelay: 5e3,
        arrowShow: !0,
        arrowSize: 5,
        breakNewLines: !0,
        elementPosition: "bottom",
        globalPosition: "top right",
        style: "bootstrap",
        className: "error",
        showAnimation: "slideDown",
        showDuration: 400,
        hideAnimation: "slideUp",
        hideDuration: 200,
        gap: 5
    }, inherit = function (a, b) {
        var F;
        return F = function () {
        }, F.prototype = a, $.extend(!0, new F, b)
    }, defaults = function (opts) {
        return $.extend(pluginOptions, opts)
    }, createElem = function (tag) {
        return $("<" + tag + "></" + tag + ">")
    }, globalAnchors = {}, getAnchorElement = function (element) {
        var radios;
        return element.is("[type=radio]") && (radios = element.parents("form:first").find("[type=radio]").filter(function (i, e) {
            return $(e).attr("name") === element.attr("name")
        }), element = radios.first()), element
    }, incr = function (obj, pos, val) {
        var opp, temp;
        if ("string" == typeof val) val = parseInt(val, 10); else if ("number" != typeof val) return;
        if (!isNaN(val)) return opp = positions[opposites[pos.charAt(0)]], temp = pos, obj[opp] !== undefined && (pos = positions[opp.charAt(0)], val = -val), obj[pos] === undefined ? obj[pos] = val : obj[pos] += val, null
    }, realign = function (alignment, inner, outer) {
        if ("l" === alignment || "t" === alignment) return 0;
        if ("c" === alignment || "m" === alignment) return outer / 2 - inner / 2;
        if ("r" === alignment || "b" === alignment) return outer - inner;
        throw"Invalid alignment"
    }, encode = function (text) {
        return encode.e = encode.e || createElem("div"), encode.e.text(text).html()
    }, Notification = function () {
        function Notification(elem, data, options) {
            "string" == typeof options && (options = {className: options}), this.options = inherit(pluginOptions, $.isPlainObject(options) ? options : {}), this.loadHTML(), this.wrapper = $(coreStyle.html), this.wrapper.data(pluginClassName, this), this.arrow = this.wrapper.find("." + pluginClassName + "-arrow"), this.container = this.wrapper.find("." + pluginClassName + "-container"), this.container.append(this.userContainer), elem && elem.length && (this.elementType = elem.attr("type"), this.originalElement = elem, this.elem = getAnchorElement(elem), this.elem.data(pluginClassName, this), this.elem.before(this.wrapper)), this.container.hide(), this.run(data)
        }

        return Notification.prototype.loadHTML = function () {
            var style;
            return style = this.getStyle(), this.userContainer = $(style.html), this.userFields = style.fields
        }, Notification.prototype.show = function (show, userCallback) {
            var args, callback, elems, fn, hidden, _this = this;
            if (callback = function () {
                return show || _this.elem || _this.destroy(), userCallback ? userCallback() : void 0
            }, hidden = this.container.parent().parents(":hidden").length > 0, elems = this.container.add(this.arrow), args = [], hidden && show) fn = "show"; else if (hidden && !show) fn = "hide"; else if (!hidden && show) fn = this.options.showAnimation, args.push(this.options.showDuration); else {
                if (hidden || show) return callback();
                fn = this.options.hideAnimation, args.push(this.options.hideDuration)
            }
            return args.push(callback), elems[fn].apply(elems, args)
        }, Notification.prototype.setGlobalPosition = function () {
            var align, anchor, css, key, main, pAlign, pMain, position;
            return position = this.getPosition(), pMain = position[0], pAlign = position[1], main = positions[pMain], align = positions[pAlign], key = pMain + "|" + pAlign, anchor = globalAnchors[key], anchor || (anchor = globalAnchors[key] = createElem("div"), css = {}, css[main] = 0, "middle" === align ? css.top = "45%" : "center" === align ? css.left = "45%" : css[align] = 0, anchor.css(css).addClass("" + pluginClassName + "-corner"), $("body").append(anchor)), anchor.prepend(this.wrapper)
        }, Notification.prototype.setElementPosition = function () {
            var arrowColor, arrowCss, arrowSize, color, contH, contW, css, elemH, elemIH, elemIW, elemPos, elemW, gap,
                mainFull, margin, opp, oppFull, pAlign, pArrow, pMain, pos, posFull, position, wrapPos, _i, _j, _len,
                _len1, _ref;
            for (position = this.getPosition(), pMain = position[0], pAlign = position[1], pArrow = position[2], elemPos = this.elem.position(), elemH = this.elem.outerHeight(), elemW = this.elem.outerWidth(), elemIH = this.elem.innerHeight(), elemIW = this.elem.innerWidth(), wrapPos = this.wrapper.position(), contH = this.container.height(), contW = this.container.width(), mainFull = positions[pMain], opp = opposites[pMain], oppFull = positions[opp], css = {}, css[oppFull] = "b" === pMain ? elemH : "r" === pMain ? elemW : 0, incr(css, "top", elemPos.top - wrapPos.top), incr(css, "left", elemPos.left - wrapPos.left), _ref = ["top", "left"], _i = 0, _len = _ref.length; _len > _i; _i++) pos = _ref[_i], margin = parseInt(this.elem.css("margin-" + pos), 10), margin && incr(css, pos, margin);
            if (gap = Math.max(0, this.options.gap - (this.options.arrowShow ? arrowSize : 0)), incr(css, oppFull, gap), this.options.arrowShow) {
                for (arrowSize = this.options.arrowSize, arrowCss = $.extend({}, css), arrowColor = this.userContainer.css("border-color") || this.userContainer.css("background-color") || "white", _j = 0, _len1 = mainPositions.length; _len1 > _j; _j++) pos = mainPositions[_j], posFull = positions[pos], pos !== opp && (color = posFull === mainFull ? arrowColor : "transparent", arrowCss["border-" + posFull] = "" + arrowSize + "px solid " + color);
                incr(css, positions[opp], arrowSize), __indexOf.call(mainPositions, pAlign) >= 0 && incr(arrowCss, positions[pAlign], 2 * arrowSize)
            } else this.arrow.hide();
            return __indexOf.call(vAligns, pMain) >= 0 ? (incr(css, "left", realign(pAlign, contW, elemW)), arrowCss && incr(arrowCss, "left", realign(pAlign, arrowSize, elemIW))) : __indexOf.call(hAligns, pMain) >= 0 && (incr(css, "top", realign(pAlign, contH, elemH)), arrowCss && incr(arrowCss, "top", realign(pAlign, arrowSize, elemIH))), this.container.is(":visible") && (css.display = "block"), this.container.removeAttr("style").css(css), arrowCss ? this.arrow.removeAttr("style").css(arrowCss) : void 0
        }, Notification.prototype.getPosition = function () {
            var pos, text, _ref, _ref1, _ref2, _ref3, _ref4, _ref5;
            if (text = this.options.position || (this.elem ? this.options.elementPosition : this.options.globalPosition), pos = parsePosition(text), 0 === pos.length && (pos[0] = "b"), _ref = pos[0], __indexOf.call(mainPositions, _ref) < 0) throw"Must be one of [" + mainPositions + "]";
            return (1 === pos.length || (_ref1 = pos[0], __indexOf.call(vAligns, _ref1) >= 0 && (_ref2 = pos[1], __indexOf.call(hAligns, _ref2) < 0)) || (_ref3 = pos[0], __indexOf.call(hAligns, _ref3) >= 0 && (_ref4 = pos[1], __indexOf.call(vAligns, _ref4) < 0))) && (pos[1] = (_ref5 = pos[0], __indexOf.call(hAligns, _ref5) >= 0 ? "m" : "l")), 2 === pos.length && (pos[2] = pos[1]), pos
        }, Notification.prototype.getStyle = function (name) {
            var style;
            if (name || (name = this.options.style), name || (name = "default"), style = styles[name], !style) throw"Missing style: " + name;
            return style
        }, Notification.prototype.updateClasses = function () {
            var classes, style;
            return classes = ["base"], $.isArray(this.options.className) ? classes = classes.concat(this.options.className) : this.options.className && classes.push(this.options.className), style = this.getStyle(), classes = $.map(classes, function (n) {
                return "" + pluginClassName + "-" + style.name + "-" + n
            }).join(" "), this.userContainer.attr("class", classes)
        }, Notification.prototype.run = function (data, options) {
            var d, datas, name, type, value, _this = this;
            if ($.isPlainObject(options) ? $.extend(this.options, options) : "string" === $.type(options) && (this.options.color = options), this.container && !data) return this.show(!1), void 0;
            if (this.container || data) {
                datas = {}, $.isPlainObject(data) ? datas = data : datas[blankFieldName] = data;
                for (name in datas) d = datas[name], type = this.userFields[name], type && ("text" === type && (d = encode(d), this.options.breakNewLines && (d = d.replace(/\n/g, "<br/>"))), value = name === blankFieldName ? "" : "=" + name, find(this.userContainer, "[data-notify-" + type + value + "]").html(d));
                return this.updateClasses(), this.elem ? this.setElementPosition() : this.setGlobalPosition(), this.show(!0), this.options.autoHide ? (clearTimeout(this.autohideTimer), this.autohideTimer = setTimeout(function () {
                    return _this.show(!1)
                }, this.options.autoHideDelay)) : void 0
            }
        }, Notification.prototype.destroy = function () {
            return this.wrapper.remove()
        }, Notification
    }(), $[pluginName] = function (elem, data, options) {
        return elem && elem.nodeName || elem.jquery ? $(elem)[pluginName](data, options) : (options = data, data = elem, new Notification(null, data, options)), elem
    }, $.fn[pluginName] = function (data, options) {
        return $(this).each(function () {
            var inst;
            return inst = getAnchorElement($(this)).data(pluginClassName), inst ? inst.run(data, options) : new Notification($(this), data, options)
        }), this
    }, $.extend($[pluginName], {
        defaults: defaults,
        addStyle: addStyle,
        pluginOptions: pluginOptions,
        getStyle: getStyle,
        insertCSS: insertCSS
    }), $(function () {
        return insertCSS(coreStyle.css).attr("id", "core-notify"), $(document).on("click notify-hide", "." + pluginClassName + "-wrapper", function (e) {
            var inst;
            return inst = $(this).data(pluginClassName), inst && (inst.options.clickToHide || "notify-hide" === e.type) ? inst.show(!1) : void 0
        })
    })
}(window, document, jQuery), $.notify.addStyle("bootstrap", {
    html: "<div>\n<span data-notify-text></span>\n</div>", classes: {
        base: {
            "font-weight": "bold",
            padding: "8px 15px 8px 14px",
            "text-shadow": "0 1px 0 rgba(255, 255, 255, 0.5)",
            "background-color": "#fcf8e3",
            border: "1px solid #fbeed5",
            "border-radius": "4px",
            "white-space": "nowrap",
            "padding-left": "25px",
            "background-repeat": "no-repeat",
            "background-position": "3px 7px"
        },
        error: {
            color: "#B94A48",
            "background-color": "#F2DEDE",
            "border-color": "#EED3D7",
            "background-image": "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAtRJREFUeNqkVc1u00AQHq+dOD+0poIQfkIjalW0SEGqRMuRnHos3DjwAH0ArlyQeANOOSMeAA5VjyBxKBQhgSpVUKKQNGloFdw4cWw2jtfMOna6JOUArDTazXi/b3dm55socPqQhFka++aHBsI8GsopRJERNFlY88FCEk9Yiwf8RhgRyaHFQpPHCDmZG5oX2ui2yilkcTT1AcDsbYC1NMAyOi7zTX2Agx7A9luAl88BauiiQ/cJaZQfIpAlngDcvZZMrl8vFPK5+XktrWlx3/ehZ5r9+t6e+WVnp1pxnNIjgBe4/6dAysQc8dsmHwPcW9C0h3fW1hans1ltwJhy0GxK7XZbUlMp5Ww2eyan6+ft/f2FAqXGK4CvQk5HueFz7D6GOZtIrK+srupdx1GRBBqNBtzc2AiMr7nPplRdKhb1q6q6zjFhrklEFOUutoQ50xcX86ZlqaZpQrfbBdu2R6/G19zX6XSgh6RX5ubyHCM8nqSID6ICrGiZjGYYxojEsiw4PDwMSL5VKsC8Yf4VRYFzMzMaxwjlJSlCyAQ9l0CW44PBADzXhe7xMdi9HtTrdYjFYkDQL0cn4Xdq2/EAE+InCnvADTf2eah4Sx9vExQjkqXT6aAERICMewd/UAp/IeYANM2joxt+q5VI+ieq2i0Wg3l6DNzHwTERPgo1ko7XBXj3vdlsT2F+UuhIhYkp7u7CarkcrFOCtR3H5JiwbAIeImjT/YQKKBtGjRFCU5IUgFRe7fF4cCNVIPMYo3VKqxwjyNAXNepuopyqnld602qVsfRpEkkz+GFL1wPj6ySXBpJtWVa5xlhpcyhBNwpZHmtX8AGgfIExo0ZpzkWVTBGiXCSEaHh62/PoR0p/vHaczxXGnj4bSo+G78lELU80h1uogBwWLf5YlsPmgDEd4M236xjm+8nm4IuE/9u+/PH2JXZfbwz4zw1WbO+SQPpXfwG/BBgAhCNZiSb/pOQAAAAASUVORK5CYII=)"
        },
        success: {
            color: "#468847",
            "background-color": "#DFF0D8",
            "border-color": "#D6E9C6",
            "background-image": "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAutJREFUeNq0lctPE0Ecx38zu/RFS1EryqtgJFA08YCiMZIAQQ4eRG8eDGdPJiYeTIwHTfwPiAcvXIwXLwoXPaDxkWgQ6islKlJLSQWLUraPLTv7Gme32zoF9KSTfLO7v53vZ3d/M7/fIth+IO6INt2jjoA7bjHCJoAlzCRw59YwHYjBnfMPqAKWQYKjGkfCJqAF0xwZjipQtA3MxeSG87VhOOYegVrUCy7UZM9S6TLIdAamySTclZdYhFhRHloGYg7mgZv1Zzztvgud7V1tbQ2twYA34LJmF4p5dXF1KTufnE+SxeJtuCZNsLDCQU0+RyKTF27Unw101l8e6hns3u0PBalORVVVkcaEKBJDgV3+cGM4tKKmI+ohlIGnygKX00rSBfszz/n2uXv81wd6+rt1orsZCHRdr1Imk2F2Kob3hutSxW8thsd8AXNaln9D7CTfA6O+0UgkMuwVvEFFUbbAcrkcTA8+AtOk8E6KiQiDmMFSDqZItAzEVQviRkdDdaFgPp8HSZKAEAL5Qh7Sq2lIJBJwv2scUqkUnKoZgNhcDKhKg5aH+1IkcouCAdFGAQsuWZYhOjwFHQ96oagWgRoUov1T9kRBEODAwxM2QtEUl+Wp+Ln9VRo6BcMw4ErHRYjH4/B26AlQoQQTRdHWwcd9AH57+UAXddvDD37DmrBBV34WfqiXPl61g+vr6xA9zsGeM9gOdsNXkgpEtTwVvwOklXLKm6+/p5ezwk4B+j6droBs2CsGa/gNs6RIxazl4Tc25mpTgw/apPR1LYlNRFAzgsOxkyXYLIM1V8NMwyAkJSctD1eGVKiq5wWjSPdjmeTkiKvVW4f2YPHWl3GAVq6ymcyCTgovM3FzyRiDe2TaKcEKsLpJvNHjZgPNqEtyi6mZIm4SRFyLMUsONSSdkPeFtY1n0mczoY3BHTLhwPRy9/lzcziCw9ACI+yql0VLzcGAZbYSM5CCSZg1/9oc/nn7+i8N9p/8An4JMADxhH+xHfuiKwAAAABJRU5ErkJggg==)"
        },
        info: {
            color: "#3A87AD",
            "background-color": "#D9EDF7",
            "border-color": "#BCE8F1",
            "background-image": "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QYFAhkSsdes/QAAA8dJREFUOMvVlGtMW2UYx//POaWHXg6lLaW0ypAtw1UCgbniNOLcVOLmAjHZolOYlxmTGXVZdAnRfXQm+7SoU4mXaOaiZsEpC9FkiQs6Z6bdCnNYruM6KNBw6YWewzl9z+sHImEWv+vz7XmT95f/+3/+7wP814v+efDOV3/SoX3lHAA+6ODeUFfMfjOWMADgdk+eEKz0pF7aQdMAcOKLLjrcVMVX3xdWN29/GhYP7SvnP0cWfS8caSkfHZsPE9Fgnt02JNutQ0QYHB2dDz9/pKX8QjjuO9xUxd/66HdxTeCHZ3rojQObGQBcuNjfplkD3b19Y/6MrimSaKgSMmpGU5WevmE/swa6Oy73tQHA0Rdr2Mmv/6A1n9w9suQ7097Z9lM4FlTgTDrzZTu4StXVfpiI48rVcUDM5cmEksrFnHxfpTtU/3BFQzCQF/2bYVoNbH7zmItbSoMj40JSzmMyX5qDvriA7QdrIIpA+3cdsMpu0nXI8cV0MtKXCPZev+gCEM1S2NHPvWfP/hL+7FSr3+0p5RBEyhEN5JCKYr8XnASMT0xBNyzQGQeI8fjsGD39RMPk7se2bd5ZtTyoFYXftF6y37gx7NeUtJJOTFlAHDZLDuILU3j3+H5oOrD3yWbIztugaAzgnBKJuBLpGfQrS8wO4FZgV+c1IxaLgWVU0tMLEETCos4xMzEIv9cJXQcyagIwigDGwJgOAtHAwAhisQUjy0ORGERiELgG4iakkzo4MYAxcM5hAMi1WWG1yYCJIcMUaBkVRLdGeSU2995TLWzcUAzONJ7J6FBVBYIggMzmFbvdBV44Corg8vjhzC+EJEl8U1kJtgYrhCzgc/vvTwXKSib1paRFVRVORDAJAsw5FuTaJEhWM2SHB3mOAlhkNxwuLzeJsGwqWzf5TFNdKgtY5qHp6ZFf67Y/sAVadCaVY5YACDDb3Oi4NIjLnWMw2QthCBIsVhsUTU9tvXsjeq9+X1d75/KEs4LNOfcdf/+HthMnvwxOD0wmHaXr7ZItn2wuH2SnBzbZAbPJwpPx+VQuzcm7dgRCB57a1uBzUDRL4bfnI0RE0eaXd9W89mpjqHZnUI5Hh2l2dkZZUhOqpi2qSmpOmZ64Tuu9qlz/SEXo6MEHa3wOip46F1n7633eekV8ds8Wxjn37Wl63VVa+ej5oeEZ/82ZBETJjpJ1Rbij2D3Z/1trXUvLsblCK0XfOx0SX2kMsn9dX+d+7Kf6h8o4AIykuffjT8L20LU+w4AZd5VvEPY+XpWqLV327HR7DzXuDnD8r+ovkBehJ8i+y8YAAAAASUVORK5CYII=)"
        },
        warn: {
            color: "#C09853",
            "background-color": "#FCF8E3",
            "border-color": "#FBEED5",
            "background-image": "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAMAAAC6V+0/AAABJlBMVEXr6eb/2oD/wi7/xjr/0mP/ykf/tQD/vBj/3o7/uQ//vyL/twebhgD/4pzX1K3z8e349vK6tHCilCWbiQymn0jGworr6dXQza3HxcKkn1vWvV/5uRfk4dXZ1bD18+/52YebiAmyr5S9mhCzrWq5t6ufjRH54aLs0oS+qD751XqPhAybhwXsujG3sm+Zk0PTwG6Shg+PhhObhwOPgQL4zV2nlyrf27uLfgCPhRHu7OmLgAafkyiWkD3l49ibiAfTs0C+lgCniwD4sgDJxqOilzDWowWFfAH08uebig6qpFHBvH/aw26FfQTQzsvy8OyEfz20r3jAvaKbhgG9q0nc2LbZxXanoUu/u5WSggCtp1anpJKdmFz/zlX/1nGJiYmuq5Dx7+sAAADoPUZSAAAAAXRSTlMAQObYZgAAAAFiS0dEAIgFHUgAAAAJcEhZcwAACxMAAAsTAQCanBgAAAAHdElNRQfdBgUBGhh4aah5AAAAlklEQVQY02NgoBIIE8EUcwn1FkIXM1Tj5dDUQhPU502Mi7XXQxGz5uVIjGOJUUUW81HnYEyMi2HVcUOICQZzMMYmxrEyMylJwgUt5BljWRLjmJm4pI1hYp5SQLGYxDgmLnZOVxuooClIDKgXKMbN5ggV1ACLJcaBxNgcoiGCBiZwdWxOETBDrTyEFey0jYJ4eHjMGWgEAIpRFRCUt08qAAAAAElFTkSuQmCC)"
        }
    }
});