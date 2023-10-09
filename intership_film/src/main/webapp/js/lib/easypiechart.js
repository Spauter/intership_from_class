!function (root, factory) {
    "object" == typeof exports ? module.exports = factory(require("jquery")) : "function" == typeof define && define.amd ? define("EasyPieChart", ["jquery"], factory) : factory(root.jQuery)
}(this, function ($) {
    var CanvasRenderer = function (el, options) {
        var cachedBackground, canvas = document.createElement("canvas");
        "undefined" != typeof G_vmlCanvasManager && G_vmlCanvasManager.initElement(canvas);
        var ctx = canvas.getContext("2d");
        canvas.width = canvas.height = options.size, el.appendChild(canvas);
        var scaleBy = 1;
        window.devicePixelRatio > 1 && (scaleBy = window.devicePixelRatio, canvas.style.width = canvas.style.height = [options.size, "px"].join(""), canvas.width = canvas.height = options.size * scaleBy, ctx.scale(scaleBy, scaleBy)), ctx.translate(options.size / 2, options.size / 2), ctx.rotate((-0.5 + options.rotate / 180) * Math.PI);
        var radius = (options.size - options.lineWidth) / 2;
        options.scaleColor && options.scaleLength && (radius -= options.scaleLength + 2), Date.now = Date.now || function () {
            return +new Date
        };
        var drawCircle = function (color, lineWidth, percent) {
            percent = Math.min(Math.max(-1, percent || 0), 1);
            var isNegative = 0 >= percent ? !0 : !1;
            ctx.beginPath(), ctx.arc(0, 0, radius, 0, 2 * Math.PI * percent, isNegative), ctx.strokeStyle = color, ctx.lineWidth = lineWidth, ctx.stroke()
        }, drawScale = function () {
            var offset, length, i = 24;
            ctx.lineWidth = 1, ctx.fillStyle = options.scaleColor, ctx.save();
            for (var i = 24; i > 0; --i) i % 6 === 0 ? (length = options.scaleLength, offset = 0) : (length = .6 * options.scaleLength, offset = options.scaleLength - length), ctx.fillRect(-options.size / 2 + offset, 0, length, 1), ctx.rotate(Math.PI / 12);
            ctx.restore()
        }, reqAnimationFrame = function () {
            return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || function (callback) {
                window.setTimeout(callback, 1e3 / 60)
            }
        }(), drawBackground = function () {
            options.scaleColor && drawScale(), options.trackColor && drawCircle(options.trackColor, options.lineWidth, 1)
        };
        this.clear = function () {
            ctx.clearRect(options.size / -2, options.size / -2, options.size, options.size)
        }, this.draw = function (percent) {
            options.scaleColor || options.trackColor ? ctx.getImageData && ctx.putImageData ? cachedBackground ? ctx.putImageData(cachedBackground, 0, 0) : (drawBackground(), cachedBackground = ctx.getImageData(0, 0, options.size * scaleBy, options.size * scaleBy)) : (this.clear(), drawBackground()) : this.clear(), ctx.lineCap = options.lineCap;
            var color;
            color = "function" == typeof options.barColor ? options.barColor(percent) : options.barColor, drawCircle(color, options.lineWidth, percent / 100)
        }.bind(this), this.animate = function (from, to) {
            var startTime = Date.now();
            options.onStart(from, to);
            var animation = function () {
                var process = Math.min(Date.now() - startTime, options.animate.duration),
                    currentValue = options.easing(this, process, from, to - from, options.animate.duration);
                this.draw(currentValue), options.onStep(from, to, currentValue), process >= options.animate.duration ? options.onStop(from, to) : reqAnimationFrame(animation)
            }.bind(this);
            reqAnimationFrame(animation)
        }.bind(this)
    }, EasyPieChart = function (el, opts) {
        var defaultOptions = {
            barColor: "#ef1e25",
            trackColor: "#f9f9f9",
            scaleColor: "#dfe0e0",
            scaleLength: 5,
            lineCap: "round",
            lineWidth: 3,
            size: 110,
            rotate: 0,
            animate: {duration: 1e3, enabled: !0},
            easing: function (x, t, b, c, d) {
                return t /= d / 2, 1 > t ? c / 2 * t * t + b : -c / 2 * (--t * (t - 2) - 1) + b
            },
            onStart: function () {
            },
            onStep: function () {
            },
            onStop: function () {
            }
        };
        if ("undefined" != typeof CanvasRenderer) defaultOptions.renderer = CanvasRenderer; else {
            if ("undefined" == typeof SVGRenderer) throw new Error("Please load either the SVG- or the CanvasRenderer");
            defaultOptions.renderer = SVGRenderer
        }
        var options = {}, currentValue = 0, init = function () {
            this.el = el, this.options = options;
            for (var i in defaultOptions) defaultOptions.hasOwnProperty(i) && (options[i] = opts && "undefined" != typeof opts[i] ? opts[i] : defaultOptions[i], "function" == typeof options[i] && (options[i] = options[i].bind(this)));
            options.easing = "string" == typeof options.easing && "undefined" != typeof jQuery && jQuery.isFunction(jQuery.easing[options.easing]) ? jQuery.easing[options.easing] : defaultOptions.easing, "number" == typeof options.animate && (options.animate = {
                duration: options.animate,
                enabled: !0
            }), "boolean" != typeof options.animate || options.animate || (options.animate = {
                duration: 1e3,
                enabled: options.animate
            }), this.renderer = new options.renderer(el, options), this.renderer.draw(currentValue), el.dataset && el.dataset.percent ? this.update(parseFloat(el.dataset.percent)) : el.getAttribute && el.getAttribute("data-percent") && this.update(parseFloat(el.getAttribute("data-percent")))
        }.bind(this);
        this.update = function (newValue) {
            return newValue = parseFloat(newValue), options.animate.enabled ? this.renderer.animate(currentValue, newValue) : this.renderer.draw(newValue), currentValue = newValue, this
        }.bind(this), this.disableAnimation = function () {
            return options.animate.enabled = !1, this
        }, this.enableAnimation = function () {
            return options.animate.enabled = !0, this
        }, init()
    };
    $.fn.easyPieChart = function (options) {
        return this.each(function () {
            var instanceOptions;
            $.data(this, "easyPieChart") || (instanceOptions = $.extend({}, options, $(this).data()), $.data(this, "easyPieChart", new EasyPieChart(this, instanceOptions)))
        })
    }
});