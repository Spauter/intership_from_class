!function (document, Math, undefined) {
    !function (factory) {
        "function" == typeof define && define.amd ? define(["jquery"], factory) : jQuery && !jQuery.fn.sparkline && factory(jQuery)
    }(function ($) {
        "use strict";
        var getDefaults, createClass, SPFormat, clipval, quartile, normalizeValue, normalizeValues, remove, isNumber,
            all, sum, addCSS, ensureArray, formatNumber, RangeMap, MouseHandler, Tooltip, barHighlightMixin, line, bar,
            tristate, discrete, bullet, pie, box, defaultStyles, initStyles, VShape, VCanvas_base, VCanvas_canvas,
            VCanvas_vml, pending, UNSET_OPTION = {}, shapeCount = 0;
        getDefaults = function () {
            return {
                common: {
                    type: "line",
                    lineColor: "#00f",
                    fillColor: "#cdf",
                    defaultPixelsPerValue: 3,
                    width: "auto",
                    height: "auto",
                    composite: !1,
                    tagValuesAttribute: "values",
                    tagOptionsPrefix: "spark",
                    enableTagOptions: !1,
                    enableHighlight: !0,
                    highlightLighten: 1.4,
                    tooltipSkipNull: !0,
                    tooltipPrefix: "",
                    tooltipSuffix: "",
                    disableHiddenCheck: !1,
                    numberFormatter: !1,
                    numberDigitGroupCount: 3,
                    numberDigitGroupSep: ",",
                    numberDecimalMark: ".",
                    disableTooltips: !1,
                    disableInteraction: !1
                },
                line: {
                    spotColor: "#f80",
                    highlightSpotColor: "#5f5",
                    highlightLineColor: "#f22",
                    spotRadius: 1.5,
                    minSpotColor: "#f80",
                    maxSpotColor: "#f80",
                    lineWidth: 1,
                    normalRangeMin: undefined,
                    normalRangeMax: undefined,
                    normalRangeColor: "#ccc",
                    drawNormalOnTop: !1,
                    chartRangeMin: undefined,
                    chartRangeMax: undefined,
                    chartRangeMinX: undefined,
                    chartRangeMaxX: undefined,
                    tooltipFormat: new SPFormat('<span style="color: {{color}}">&#9679;</span> {{prefix}}{{y}}{{suffix}}')
                },
                bar: {
                    barColor: "#3366cc",
                    negBarColor: "#f44",
                    stackedBarColor: ["#3366cc", "#dc3912", "#ff9900", "#109618", "#66aa00", "#dd4477", "#0099c6", "#990099"],
                    zeroColor: undefined,
                    nullColor: undefined,
                    zeroAxis: !0,
                    barWidth: 4,
                    barSpacing: 1,
                    chartRangeMax: undefined,
                    chartRangeMin: undefined,
                    chartRangeClip: !1,
                    colorMap: undefined,
                    tooltipFormat: new SPFormat('<span style="color: {{color}}">&#9679;</span> {{prefix}}{{value}}{{suffix}}')
                },
                tristate: {
                    barWidth: 4,
                    barSpacing: 1,
                    posBarColor: "#6f6",
                    negBarColor: "#f44",
                    zeroBarColor: "#999",
                    colorMap: {},
                    tooltipFormat: new SPFormat('<span style="color: {{color}}">&#9679;</span> {{value:map}}'),
                    tooltipValueLookups: {map: {"-1": "Loss", 0: "Draw", 1: "Win"}}
                },
                discrete: {
                    lineHeight: "auto",
                    thresholdColor: undefined,
                    thresholdValue: 0,
                    chartRangeMax: undefined,
                    chartRangeMin: undefined,
                    chartRangeClip: !1,
                    tooltipFormat: new SPFormat("{{prefix}}{{value}}{{suffix}}")
                },
                bullet: {
                    targetColor: "#f33",
                    targetWidth: 3,
                    performanceColor: "#33f",
                    rangeColors: ["#d3dafe", "#a8b6ff", "#7f94ff"],
                    base: undefined,
                    tooltipFormat: new SPFormat("{{fieldkey:fields}} - {{value}}"),
                    tooltipValueLookups: {fields: {r: "Range", p: "Performance", t: "Target"}}
                },
                pie: {
                    offset: 0,
                    sliceColors: ["#3366cc", "#dc3912", "#ff9900", "#109618", "#66aa00", "#dd4477", "#0099c6", "#990099"],
                    borderWidth: 0,
                    borderColor: "#000",
                    tooltipFormat: new SPFormat('<span style="color: {{color}}">&#9679;</span> {{value}} ({{percent.1}}%)')
                },
                box: {
                    raw: !1,
                    boxLineColor: "#000",
                    boxFillColor: "#cdf",
                    whiskerColor: "#000",
                    outlierLineColor: "#333",
                    outlierFillColor: "#fff",
                    medianColor: "#f00",
                    showOutliers: !0,
                    outlierIQR: 1.5,
                    spotRadius: 1.5,
                    target: undefined,
                    targetColor: "#4a2",
                    chartRangeMax: undefined,
                    chartRangeMin: undefined,
                    tooltipFormat: new SPFormat("{{field:fields}}: {{value}}"),
                    tooltipFormatFieldlistKey: "field",
                    tooltipValueLookups: {
                        fields: {
                            lq: "Lower Quartile",
                            med: "Median",
                            uq: "Upper Quartile",
                            lo: "Left Outlier",
                            ro: "Right Outlier",
                            lw: "Left Whisker",
                            rw: "Right Whisker"
                        }
                    }
                }
            }
        }, defaultStyles = '.jqstooltip { position: absolute;left: 0px;top: 0px;visibility: hidden;background: rgb(0, 0, 0) transparent;background-color: rgba(0,0,0,0.6);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000);-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";color: white;font: 10px arial, san serif;text-align: left;white-space: nowrap;padding: 5px;border: 1px solid white;z-index: 10000;}.jqsfield { color: white;font: 10px arial, san serif;text-align: left;}', createClass = function () {
            var Class, args;
            return Class = function () {
                this.init.apply(this, arguments)
            }, arguments.length > 1 ? (arguments[0] ? (Class.prototype = $.extend(new arguments[0], arguments[arguments.length - 1]), Class._super = arguments[0].prototype) : Class.prototype = arguments[arguments.length - 1], arguments.length > 2 && (args = Array.prototype.slice.call(arguments, 1, -1), args.unshift(Class.prototype), $.extend.apply($, args))) : Class.prototype = arguments[0], Class.prototype.cls = Class, Class
        }, $.SPFormatClass = SPFormat = createClass({
            fre: /\{\{([\w.]+?)(:(.+?))?\}\}/g,
            precre: /(\w+)\.(\d+)/,
            init: function (format, fclass) {
                this.format = format, this.fclass = fclass
            },
            render: function (fieldset, lookups, options) {
                var match, token, lookupkey, fieldvalue, prec, self = this, fields = fieldset;
                return this.format.replace(this.fre, function () {
                    var lookup;
                    return token = arguments[1], lookupkey = arguments[3], match = self.precre.exec(token), match ? (prec = match[2], token = match[1]) : prec = !1, fieldvalue = fields[token], fieldvalue === undefined ? "" : lookupkey && lookups && lookups[lookupkey] ? (lookup = lookups[lookupkey], lookup.get ? lookups[lookupkey].get(fieldvalue) || fieldvalue : lookups[lookupkey][fieldvalue] || fieldvalue) : (isNumber(fieldvalue) && (fieldvalue = options.get("numberFormatter") ? options.get("numberFormatter")(fieldvalue) : formatNumber(fieldvalue, prec, options.get("numberDigitGroupCount"), options.get("numberDigitGroupSep"), options.get("numberDecimalMark"))), fieldvalue)
                })
            }
        }), $.spformat = function (format, fclass) {
            return new SPFormat(format, fclass)
        }, clipval = function (val, min, max) {
            return min > val ? min : val > max ? max : val
        }, quartile = function (values, q) {
            var vl;
            return 2 === q ? (vl = Math.floor(values.length / 2), values.length % 2 ? values[vl] : (values[vl - 1] + values[vl]) / 2) : values.length % 2 ? (vl = (values.length * q + q) / 4, vl % 1 ? (values[Math.floor(vl)] + values[Math.floor(vl) - 1]) / 2 : values[vl - 1]) : (vl = (values.length * q + 2) / 4, vl % 1 ? (values[Math.floor(vl)] + values[Math.floor(vl) - 1]) / 2 : values[vl - 1])
        }, normalizeValue = function (val) {
            var nf;
            switch (val) {
                case"undefined":
                    val = undefined;
                    break;
                case"null":
                    val = null;
                    break;
                case"true":
                    val = !0;
                    break;
                case"false":
                    val = !1;
                    break;
                default:
                    nf = parseFloat(val), val == nf && (val = nf)
            }
            return val
        }, normalizeValues = function (vals) {
            var i, result = [];
            for (i = vals.length; i--;) result[i] = normalizeValue(vals[i]);
            return result
        }, remove = function (vals, filter) {
            var i, vl, result = [];
            for (i = 0, vl = vals.length; vl > i; i++) vals[i] !== filter && result.push(vals[i]);
            return result
        }, isNumber = function (num) {
            return !isNaN(parseFloat(num)) && isFinite(num)
        }, formatNumber = function (num, prec, groupsize, groupsep, decsep) {
            var p, i;
            for (num = (prec === !1 ? parseFloat(num).toString() : num.toFixed(prec)).split(""), p = (p = $.inArray(".", num)) < 0 ? num.length : p, p < num.length && (num[p] = decsep), i = p - groupsize; i > 0; i -= groupsize) num.splice(i, 0, groupsep);
            return num.join("")
        }, all = function (val, arr, ignoreNull) {
            var i;
            for (i = arr.length; i--;) if ((!ignoreNull || null !== arr[i]) && arr[i] !== val) return !1;
            return !0
        }, sum = function (vals) {
            var i, total = 0;
            for (i = vals.length; i--;) total += "number" == typeof vals[i] ? vals[i] : 0;
            return total
        }, ensureArray = function (val) {
            return $.isArray(val) ? val : [val]
        }, addCSS = function (css) {
            var tag;
            document.createStyleSheet ? document.createStyleSheet().cssText = css : (tag = document.createElement("style"), tag.type = "text/css", document.getElementsByTagName("head")[0].appendChild(tag), tag["string" == typeof document.body.style.WebkitAppearance ? "innerText" : "innerHTML"] = css)
        }, $.fn.simpledraw = function (width, height, useExisting, interact) {
            var target, mhandler;
            if (useExisting && (target = this.data("_jqs_vcanvas"))) return target;
            if ($.fn.sparkline.canvas === !1) return !1;
            if ($.fn.sparkline.canvas === undefined) {
                var el = document.createElement("canvas");
                if (el.getContext && el.getContext("2d")) $.fn.sparkline.canvas = function (width, height, target, interact) {
                    return new VCanvas_canvas(width, height, target, interact)
                }; else {
                    if (!document.namespaces || document.namespaces.v) return $.fn.sparkline.canvas = !1, !1;
                    document.namespaces.add("v", "urn:schemas-microsoft-com:vml", "#default#VML"), $.fn.sparkline.canvas = function (width, height, target) {
                        return new VCanvas_vml(width, height, target)
                    }
                }
            }
            return width === undefined && (width = $(this).innerWidth()), height === undefined && (height = $(this).innerHeight()), target = $.fn.sparkline.canvas(width, height, this, interact), mhandler = $(this).data("_jqs_mhandler"), mhandler && mhandler.registerCanvas(target), target
        }, $.fn.cleardraw = function () {
            var target = this.data("_jqs_vcanvas");
            target && target.reset()
        }, $.RangeMapClass = RangeMap = createClass({
            init: function (map) {
                var key, range, rangelist = [];
                for (key in map) map.hasOwnProperty(key) && "string" == typeof key && key.indexOf(":") > -1 && (range = key.split(":"), range[0] = 0 === range[0].length ? -1 / 0 : parseFloat(range[0]), range[1] = 0 === range[1].length ? 1 / 0 : parseFloat(range[1]), range[2] = map[key], rangelist.push(range));
                this.map = map, this.rangelist = rangelist || !1
            }, get: function (value) {
                var i, range, result, rangelist = this.rangelist;
                if ((result = this.map[value]) !== undefined) return result;
                if (rangelist) for (i = rangelist.length; i--;) if (range = rangelist[i], range[0] <= value && range[1] >= value) return range[2];
                return undefined
            }
        }), $.range_map = function (map) {
            return new RangeMap(map)
        }, MouseHandler = createClass({
            init: function (el, options) {
                var $el = $(el);
                this.$el = $el, this.options = options, this.currentPageX = 0, this.currentPageY = 0, this.el = el, this.splist = [], this.tooltip = null, this.over = !1, this.displayTooltips = !options.get("disableTooltips"), this.highlightEnabled = !options.get("disableHighlight")
            }, registerSparkline: function (sp) {
                this.splist.push(sp), this.over && this.updateDisplay()
            }, registerCanvas: function (canvas) {
                var $canvas = $(canvas.canvas);
                this.canvas = canvas, this.$canvas = $canvas, $canvas.mouseenter($.proxy(this.mouseenter, this)), $canvas.mouseleave($.proxy(this.mouseleave, this)), $canvas.click($.proxy(this.mouseclick, this))
            }, reset: function (removeTooltip) {
                this.splist = [], this.tooltip && removeTooltip && (this.tooltip.remove(), this.tooltip = undefined)
            }, mouseclick: function (e) {
                var clickEvent = $.Event("sparklineClick");
                clickEvent.originalEvent = e, clickEvent.sparklines = this.splist, this.$el.trigger(clickEvent)
            }, mouseenter: function (e) {
                $(document.body).unbind("mousemove.jqs"), $(document.body).bind("mousemove.jqs", $.proxy(this.mousemove, this)), this.over = !0, this.currentPageX = e.pageX, this.currentPageY = e.pageY, this.currentEl = e.target, !this.tooltip && this.displayTooltips && (this.tooltip = new Tooltip(this.options), this.tooltip.updatePosition(e.pageX, e.pageY)), this.updateDisplay()
            }, mouseleave: function () {
                $(document.body).unbind("mousemove.jqs");
                var sp, i, splist = this.splist, spcount = splist.length, needsRefresh = !1;
                for (this.over = !1, this.currentEl = null, this.tooltip && (this.tooltip.remove(), this.tooltip = null), i = 0; spcount > i; i++) sp = splist[i], sp.clearRegionHighlight() && (needsRefresh = !0);
                needsRefresh && this.canvas.render()
            }, mousemove: function (e) {
                this.currentPageX = e.pageX, this.currentPageY = e.pageY, this.currentEl = e.target, this.tooltip && this.tooltip.updatePosition(e.pageX, e.pageY), this.updateDisplay()
            }, updateDisplay: function () {
                var tooltiphtml, sp, i, result, changeEvent, splist = this.splist, spcount = splist.length,
                    needsRefresh = !1, offset = this.$canvas.offset(), localX = this.currentPageX - offset.left,
                    localY = this.currentPageY - offset.top;
                if (this.over) {
                    for (i = 0; spcount > i; i++) sp = splist[i], result = sp.setRegionHighlight(this.currentEl, localX, localY), result && (needsRefresh = !0);
                    if (needsRefresh) {
                        if (changeEvent = $.Event("sparklineRegionChange"), changeEvent.sparklines = this.splist, this.$el.trigger(changeEvent), this.tooltip) {
                            for (tooltiphtml = "", i = 0; spcount > i; i++) sp = splist[i], tooltiphtml += sp.getCurrentRegionTooltip();
                            this.tooltip.setContent(tooltiphtml)
                        }
                        this.disableHighlight || this.canvas.render()
                    }
                    null === result && this.mouseleave()
                }
            }
        }), Tooltip = createClass({
            sizeStyle: "position: static !important;display: block !important;visibility: hidden !important;float: left !important;",
            init: function (options) {
                var offset, tooltipClassname = options.get("tooltipClassname", "jqstooltip"),
                    sizetipStyle = this.sizeStyle;
                this.container = options.get("tooltipContainer") || document.body, this.tooltipOffsetX = options.get("tooltipOffsetX", 10), this.tooltipOffsetY = options.get("tooltipOffsetY", 12), $("#jqssizetip").remove(), $("#jqstooltip").remove(), this.sizetip = $("<div/>", {
                    id: "jqssizetip",
                    style: sizetipStyle,
                    "class": tooltipClassname
                }), this.tooltip = $("<div/>", {
                    id: "jqstooltip",
                    "class": tooltipClassname
                }).appendTo(this.container), offset = this.tooltip.offset(), this.offsetLeft = offset.left, this.offsetTop = offset.top, this.hidden = !0, $(window).unbind("resize.jqs scroll.jqs"), $(window).bind("resize.jqs scroll.jqs", $.proxy(this.updateWindowDims, this)), this.updateWindowDims()
            },
            updateWindowDims: function () {
                this.scrollTop = $(window).scrollTop(), this.scrollLeft = $(window).scrollLeft(), this.scrollRight = this.scrollLeft + $(window).width(), this.updatePosition()
            },
            getSize: function (content) {
                this.sizetip.html(content).appendTo(this.container), this.width = this.sizetip.width() + 1, this.height = this.sizetip.height(), this.sizetip.remove()
            },
            setContent: function (content) {
                return content ? (this.getSize(content), this.tooltip.html(content).css({
                    width: this.width,
                    height: this.height,
                    visibility: "visible"
                }), this.hidden && (this.hidden = !1, this.updatePosition()), void 0) : (this.tooltip.css("visibility", "hidden"), this.hidden = !0, void 0)
            },
            updatePosition: function (x, y) {
                if (x === undefined) {
                    if (this.mousex === undefined) return;
                    x = this.mousex - this.offsetLeft, y = this.mousey - this.offsetTop
                } else this.mousex = x -= this.offsetLeft, this.mousey = y -= this.offsetTop;
                this.height && this.width && !this.hidden && (y -= this.height + this.tooltipOffsetY, x += this.tooltipOffsetX, y < this.scrollTop && (y = this.scrollTop), x < this.scrollLeft ? x = this.scrollLeft : x + this.width > this.scrollRight && (x = this.scrollRight - this.width), this.tooltip.css({
                    left: x,
                    top: y
                }))
            },
            remove: function () {
                this.tooltip.remove(), this.sizetip.remove(), this.sizetip = this.tooltip = undefined, $(window).unbind("resize.jqs scroll.jqs")
            }
        }), initStyles = function () {
            addCSS(defaultStyles)
        }, $(initStyles), pending = [], $.fn.sparkline = function (userValues, userOptions) {
            return this.each(function () {
                var render, i, options = new $.fn.sparkline.options(this, userOptions), $this = $(this);
                if (render = function () {
                    var values, width, height, tmp, mhandler, sp, vals;
                    return "html" === userValues || userValues === undefined ? (vals = this.getAttribute(options.get("tagValuesAttribute")), (vals === undefined || null === vals) && (vals = $this.html()), values = vals.replace(/(^\s*<!--)|(-->\s*$)|\s+/g, "").split(",")) : values = userValues, width = "auto" === options.get("width") ? values.length * options.get("defaultPixelsPerValue") : options.get("width"), "auto" === options.get("height") ? options.get("composite") && $.data(this, "_jqs_vcanvas") || (tmp = document.createElement("span"), tmp.innerHTML = "a", $this.html(tmp), height = $(tmp).innerHeight() || $(tmp).height(), $(tmp).remove(), tmp = null) : height = options.get("height"), options.get("disableInteraction") ? mhandler = !1 : (mhandler = $.data(this, "_jqs_mhandler"), mhandler ? options.get("composite") || mhandler.reset() : (mhandler = new MouseHandler(this, options), $.data(this, "_jqs_mhandler", mhandler))), options.get("composite") && !$.data(this, "_jqs_vcanvas") ? ($.data(this, "_jqs_errnotify") || (alert("Attempted to attach a composite sparkline to an element with no existing sparkline"), $.data(this, "_jqs_errnotify", !0)), void 0) : (sp = new ($.fn.sparkline[options.get("type")])(this, values, options, width, height), sp.render(), mhandler && mhandler.registerSparkline(sp), void 0)
                }, $(this).html() && !options.get("disableHiddenCheck") && $(this).is(":hidden") || !$(this).parents("body").length) {
                    if (!options.get("composite") && $.data(this, "_jqs_pending")) for (i = pending.length; i; i--) pending[i - 1][0] == this && pending.splice(i - 1, 1);
                    pending.push([this, render]), $.data(this, "_jqs_pending", !0)
                } else render.call(this)
            })
        }, $.fn.sparkline.defaults = getDefaults(), $.sparkline_display_visible = function () {
            var el, i, pl, done = [];
            for (i = 0, pl = pending.length; pl > i; i++) el = pending[i][0], $(el).is(":visible") && !$(el).parents().is(":hidden") ? (pending[i][1].call(el), $.data(pending[i][0], "_jqs_pending", !1), done.push(i)) : $(el).closest("html").length || $.data(el, "_jqs_pending") || ($.data(pending[i][0], "_jqs_pending", !1), done.push(i));
            for (i = done.length; i; i--) pending.splice(done[i - 1], 1)
        }, $.fn.sparkline.options = createClass({
            init: function (tag, userOptions) {
                var extendedOptions, defaults, base, tagOptionType;
                this.userOptions = userOptions = userOptions || {}, this.tag = tag, this.tagValCache = {}, defaults = $.fn.sparkline.defaults, base = defaults.common, this.tagOptionsPrefix = userOptions.enableTagOptions && (userOptions.tagOptionsPrefix || base.tagOptionsPrefix), tagOptionType = this.getTagSetting("type"), extendedOptions = tagOptionType === UNSET_OPTION ? defaults[userOptions.type || base.type] : defaults[tagOptionType], this.mergedOptions = $.extend({}, base, extendedOptions, userOptions)
            }, getTagSetting: function (key) {
                var val, i, pairs, keyval, prefix = this.tagOptionsPrefix;
                if (prefix === !1 || prefix === undefined) return UNSET_OPTION;
                if (this.tagValCache.hasOwnProperty(key)) val = this.tagValCache.key; else {
                    if (val = this.tag.getAttribute(prefix + key), val === undefined || null === val) val = UNSET_OPTION; else if ("[" === val.substr(0, 1)) for (val = val.substr(1, val.length - 2).split(","), i = val.length; i--;) val[i] = normalizeValue(val[i].replace(/(^\s*)|(\s*$)/g, "")); else if ("{" === val.substr(0, 1)) for (pairs = val.substr(1, val.length - 2).split(","), val = {}, i = pairs.length; i--;) keyval = pairs[i].split(":", 2), val[keyval[0].replace(/(^\s*)|(\s*$)/g, "")] = normalizeValue(keyval[1].replace(/(^\s*)|(\s*$)/g, "")); else val = normalizeValue(val);
                    this.tagValCache.key = val
                }
                return val
            }, get: function (key, defaultval) {
                var result, tagOption = this.getTagSetting(key);
                return tagOption !== UNSET_OPTION ? tagOption : (result = this.mergedOptions[key]) === undefined ? defaultval : result
            }
        }), $.fn.sparkline._base = createClass({
            disabled: !1, init: function (el, values, options, width, height) {
                this.el = el, this.$el = $(el), this.values = values, this.options = options, this.width = width, this.height = height, this.currentRegion = undefined
            }, initTarget: function () {
                var interactive = !this.options.get("disableInteraction");
                (this.target = this.$el.simpledraw(this.width, this.height, this.options.get("composite"), interactive)) ? (this.canvasWidth = this.target.pixelWidth, this.canvasHeight = this.target.pixelHeight) : this.disabled = !0
            }, render: function () {
                return this.disabled ? (this.el.innerHTML = "", !1) : !0
            }, getRegion: function () {
            }, setRegionHighlight: function (el, x, y) {
                var newRegion, currentRegion = this.currentRegion,
                    highlightEnabled = !this.options.get("disableHighlight");
                return x > this.canvasWidth || y > this.canvasHeight || 0 > x || 0 > y ? null : (newRegion = this.getRegion(el, x, y), currentRegion !== newRegion ? (currentRegion !== undefined && highlightEnabled && this.removeHighlight(), this.currentRegion = newRegion, newRegion !== undefined && highlightEnabled && this.renderHighlight(), !0) : !1)
            }, clearRegionHighlight: function () {
                return this.currentRegion !== undefined ? (this.removeHighlight(), this.currentRegion = undefined, !0) : !1
            }, renderHighlight: function () {
                this.changeHighlight(!0)
            }, removeHighlight: function () {
                this.changeHighlight(!1)
            }, changeHighlight: function () {
            }, getCurrentRegionTooltip: function () {
                var fields, formats, formatlen, fclass, text, i, showFields, showFieldsKey, newFields, fv, formatter,
                    format, fieldlen, j, options = this.options, header = "", entries = [];
                if (this.currentRegion === undefined) return "";
                if (fields = this.getCurrentRegionFields(), formatter = options.get("tooltipFormatter")) return formatter(this, options, fields);
                if (options.get("tooltipChartTitle") && (header += '<div class="jqs jqstitle">' + options.get("tooltipChartTitle") + "</div>\n"), formats = this.options.get("tooltipFormat"), !formats) return "";
                if ($.isArray(formats) || (formats = [formats]), $.isArray(fields) || (fields = [fields]), showFields = this.options.get("tooltipFormatFieldlist"), showFieldsKey = this.options.get("tooltipFormatFieldlistKey"), showFields && showFieldsKey) {
                    for (newFields = [], i = fields.length; i--;) fv = fields[i][showFieldsKey], -1 != (j = $.inArray(fv, showFields)) && (newFields[j] = fields[i]);
                    fields = newFields
                }
                for (formatlen = formats.length, fieldlen = fields.length, i = 0; formatlen > i; i++) for (format = formats[i], "string" == typeof format && (format = new SPFormat(format)), fclass = format.fclass || "jqsfield", j = 0; fieldlen > j; j++) fields[j].isNull && options.get("tooltipSkipNull") || ($.extend(fields[j], {
                    prefix: options.get("tooltipPrefix"),
                    suffix: options.get("tooltipSuffix")
                }), text = format.render(fields[j], options.get("tooltipValueLookups"), options), entries.push('<div class="' + fclass + '">' + text + "</div>"));
                return entries.length ? header + entries.join("\n") : ""
            }, getCurrentRegionFields: function () {
            }, calcHighlightColor: function (color, options) {
                var parse, mult, rgbnew, i, highlightColor = options.get("highlightColor"),
                    lighten = options.get("highlightLighten");
                if (highlightColor) return highlightColor;
                if (lighten && (parse = /^#([0-9a-f])([0-9a-f])([0-9a-f])$/i.exec(color) || /^#([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})$/i.exec(color))) {
                    for (rgbnew = [], mult = 4 === color.length ? 16 : 1, i = 0; 3 > i; i++) rgbnew[i] = clipval(Math.round(parseInt(parse[i + 1], 16) * mult * lighten), 0, 255);
                    return "rgb(" + rgbnew.join(",") + ")"
                }
                return color
            }
        }), barHighlightMixin = {
            changeHighlight: function (highlight) {
                var newShapes, currentRegion = this.currentRegion, target = this.target,
                    shapeids = this.regionShapes[currentRegion];
                shapeids && (newShapes = this.renderRegion(currentRegion, highlight), $.isArray(newShapes) || $.isArray(shapeids) ? (target.replaceWithShapes(shapeids, newShapes), this.regionShapes[currentRegion] = $.map(newShapes, function (newShape) {
                    return newShape.id
                })) : (target.replaceWithShape(shapeids, newShapes), this.regionShapes[currentRegion] = newShapes.id))
            }, render: function () {
                var shapes, ids, i, j, values = this.values, target = this.target, regionShapes = this.regionShapes;
                if (this.cls._super.render.call(this)) {
                    for (i = values.length; i--;) if (shapes = this.renderRegion(i)) if ($.isArray(shapes)) {
                        for (ids = [], j = shapes.length; j--;) shapes[j].append(), ids.push(shapes[j].id);
                        regionShapes[i] = ids
                    } else shapes.append(), regionShapes[i] = shapes.id; else regionShapes[i] = null;
                    target.render()
                }
            }
        }, $.fn.sparkline.line = line = createClass($.fn.sparkline._base, {
            type: "line", init: function (el, values, options, width, height) {
                line._super.init.call(this, el, values, options, width, height), this.vertices = [], this.regionMap = [], this.xvalues = [], this.yvalues = [], this.yminmax = [], this.hightlightSpotId = null, this.lastShapeId = null, this.initTarget()
            }, getRegion: function (el, x) {
                var i, regionMap = this.regionMap;
                for (i = regionMap.length; i--;) if (null !== regionMap[i] && x >= regionMap[i][0] && x <= regionMap[i][1]) return regionMap[i][2];
                return undefined
            }, getCurrentRegionFields: function () {
                var currentRegion = this.currentRegion;
                return {
                    isNull: null === this.yvalues[currentRegion],
                    x: this.xvalues[currentRegion],
                    y: this.yvalues[currentRegion],
                    color: this.options.get("lineColor"),
                    fillColor: this.options.get("fillColor"),
                    offset: currentRegion
                }
            }, renderHighlight: function () {
                var highlightSpot, highlightLine, currentRegion = this.currentRegion, target = this.target,
                    vertex = this.vertices[currentRegion], options = this.options,
                    spotRadius = options.get("spotRadius"), highlightSpotColor = options.get("highlightSpotColor"),
                    highlightLineColor = options.get("highlightLineColor");
                vertex && (spotRadius && highlightSpotColor && (highlightSpot = target.drawCircle(vertex[0], vertex[1], spotRadius, undefined, highlightSpotColor), this.highlightSpotId = highlightSpot.id, target.insertAfterShape(this.lastShapeId, highlightSpot)), highlightLineColor && (highlightLine = target.drawLine(vertex[0], this.canvasTop, vertex[0], this.canvasTop + this.canvasHeight, highlightLineColor), this.highlightLineId = highlightLine.id, target.insertAfterShape(this.lastShapeId, highlightLine)))
            }, removeHighlight: function () {
                var target = this.target;
                this.highlightSpotId && (target.removeShapeId(this.highlightSpotId), this.highlightSpotId = null), this.highlightLineId && (target.removeShapeId(this.highlightLineId), this.highlightLineId = null)
            }, scanValues: function () {
                var i, val, isStr, isArray, sp, values = this.values, valcount = values.length, xvalues = this.xvalues,
                    yvalues = this.yvalues, yminmax = this.yminmax;
                for (i = 0; valcount > i; i++) val = values[i], isStr = "string" == typeof values[i], isArray = "object" == typeof values[i] && values[i] instanceof Array, sp = isStr && values[i].split(":"), isStr && 2 === sp.length ? (xvalues.push(Number(sp[0])), yvalues.push(Number(sp[1])), yminmax.push(Number(sp[1]))) : isArray ? (xvalues.push(val[0]), yvalues.push(val[1]), yminmax.push(val[1])) : (xvalues.push(i), null === values[i] || "null" === values[i] ? yvalues.push(null) : (yvalues.push(Number(val)), yminmax.push(Number(val))));
                this.options.get("xvalues") && (xvalues = this.options.get("xvalues")), this.maxy = this.maxyorg = Math.max.apply(Math, yminmax), this.miny = this.minyorg = Math.min.apply(Math, yminmax), this.maxx = Math.max.apply(Math, xvalues), this.minx = Math.min.apply(Math, xvalues), this.xvalues = xvalues, this.yvalues = yvalues, this.yminmax = yminmax
            }, processRangeOptions: function () {
                var options = this.options, normalRangeMin = options.get("normalRangeMin"),
                    normalRangeMax = options.get("normalRangeMax");
                normalRangeMin !== undefined && (normalRangeMin < this.miny && (this.miny = normalRangeMin), normalRangeMax > this.maxy && (this.maxy = normalRangeMax)), options.get("chartRangeMin") !== undefined && (options.get("chartRangeClip") || options.get("chartRangeMin") < this.miny) && (this.miny = options.get("chartRangeMin")), options.get("chartRangeMax") !== undefined && (options.get("chartRangeClip") || options.get("chartRangeMax") > this.maxy) && (this.maxy = options.get("chartRangeMax")), options.get("chartRangeMinX") !== undefined && (options.get("chartRangeClipX") || options.get("chartRangeMinX") < this.minx) && (this.minx = options.get("chartRangeMinX")), options.get("chartRangeMaxX") !== undefined && (options.get("chartRangeClipX") || options.get("chartRangeMaxX") > this.maxx) && (this.maxx = options.get("chartRangeMaxX"))
            }, drawNormalRange: function (canvasLeft, canvasTop, canvasHeight, canvasWidth, rangey) {
                var normalRangeMin = this.options.get("normalRangeMin"),
                    normalRangeMax = this.options.get("normalRangeMax"),
                    ytop = canvasTop + Math.round(canvasHeight - canvasHeight * ((normalRangeMax - this.miny) / rangey)),
                    height = Math.round(canvasHeight * (normalRangeMax - normalRangeMin) / rangey);
                this.target.drawRect(canvasLeft, ytop, canvasWidth, height, undefined, this.options.get("normalRangeColor")).append()
            }, render: function () {
                var rangex, rangey, yvallast, canvasTop, canvasLeft, vertex, path, paths, x, y, xnext, xpos, xposnext,
                    last, next, yvalcount, lineShapes, fillShapes, plen, valueSpots, hlSpotsEnabled, color, xvalues,
                    yvalues, i, options = this.options, target = this.target, canvasWidth = this.canvasWidth,
                    canvasHeight = this.canvasHeight, vertices = this.vertices, spotRadius = options.get("spotRadius"),
                    regionMap = this.regionMap;
                if (line._super.render.call(this) && (this.scanValues(), this.processRangeOptions(), xvalues = this.xvalues, yvalues = this.yvalues, this.yminmax.length && !(this.yvalues.length < 2))) {
                    for (canvasTop = canvasLeft = 0, rangex = this.maxx - this.minx === 0 ? 1 : this.maxx - this.minx, rangey = this.maxy - this.miny === 0 ? 1 : this.maxy - this.miny, yvallast = this.yvalues.length - 1, spotRadius && (4 * spotRadius > canvasWidth || 4 * spotRadius > canvasHeight) && (spotRadius = 0), spotRadius && (hlSpotsEnabled = options.get("highlightSpotColor") && !options.get("disableInteraction"), (hlSpotsEnabled || options.get("minSpotColor") || options.get("spotColor") && yvalues[yvallast] === this.miny) && (canvasHeight -= Math.ceil(spotRadius)), (hlSpotsEnabled || options.get("maxSpotColor") || options.get("spotColor") && yvalues[yvallast] === this.maxy) && (canvasHeight -= Math.ceil(spotRadius), canvasTop += Math.ceil(spotRadius)), (hlSpotsEnabled || (options.get("minSpotColor") || options.get("maxSpotColor")) && (yvalues[0] === this.miny || yvalues[0] === this.maxy)) && (canvasLeft += Math.ceil(spotRadius), canvasWidth -= Math.ceil(spotRadius)), (hlSpotsEnabled || options.get("spotColor") || options.get("minSpotColor") || options.get("maxSpotColor") && (yvalues[yvallast] === this.miny || yvalues[yvallast] === this.maxy)) && (canvasWidth -= Math.ceil(spotRadius))), canvasHeight--, options.get("normalRangeMin") === undefined || options.get("drawNormalOnTop") || this.drawNormalRange(canvasLeft, canvasTop, canvasHeight, canvasWidth, rangey), path = [], paths = [path], last = next = null, yvalcount = yvalues.length, i = 0; yvalcount > i; i++) x = xvalues[i], xnext = xvalues[i + 1], y = yvalues[i], xpos = canvasLeft + Math.round((x - this.minx) * (canvasWidth / rangex)), xposnext = yvalcount - 1 > i ? canvasLeft + Math.round((xnext - this.minx) * (canvasWidth / rangex)) : canvasWidth, next = xpos + (xposnext - xpos) / 2, regionMap[i] = [last || 0, next, i], last = next, null === y ? i && (null !== yvalues[i - 1] && (path = [], paths.push(path)), vertices.push(null)) : (y < this.miny && (y = this.miny), y > this.maxy && (y = this.maxy), path.length || path.push([xpos, canvasTop + canvasHeight]), vertex = [xpos, canvasTop + Math.round(canvasHeight - canvasHeight * ((y - this.miny) / rangey))], path.push(vertex), vertices.push(vertex));
                    for (lineShapes = [], fillShapes = [], plen = paths.length, i = 0; plen > i; i++) path = paths[i], path.length && (options.get("fillColor") && (path.push([path[path.length - 1][0], canvasTop + canvasHeight]), fillShapes.push(path.slice(0)), path.pop()), path.length > 2 && (path[0] = [path[0][0], path[1][1]]), lineShapes.push(path));
                    for (plen = fillShapes.length, i = 0; plen > i; i++) target.drawShape(fillShapes[i], options.get("fillColor"), options.get("fillColor")).append();
                    for (options.get("normalRangeMin") !== undefined && options.get("drawNormalOnTop") && this.drawNormalRange(canvasLeft, canvasTop, canvasHeight, canvasWidth, rangey), plen = lineShapes.length, i = 0; plen > i; i++) target.drawShape(lineShapes[i], options.get("lineColor"), undefined, options.get("lineWidth")).append();
                    if (spotRadius && options.get("valueSpots")) for (valueSpots = options.get("valueSpots"), valueSpots.get === undefined && (valueSpots = new RangeMap(valueSpots)), i = 0; yvalcount > i; i++) color = valueSpots.get(yvalues[i]), color && target.drawCircle(canvasLeft + Math.round((xvalues[i] - this.minx) * (canvasWidth / rangex)), canvasTop + Math.round(canvasHeight - canvasHeight * ((yvalues[i] - this.miny) / rangey)), spotRadius, undefined, color).append();
                    spotRadius && options.get("spotColor") && null !== yvalues[yvallast] && target.drawCircle(canvasLeft + Math.round((xvalues[xvalues.length - 1] - this.minx) * (canvasWidth / rangex)), canvasTop + Math.round(canvasHeight - canvasHeight * ((yvalues[yvallast] - this.miny) / rangey)), spotRadius, undefined, options.get("spotColor")).append(), this.maxy !== this.minyorg && (spotRadius && options.get("minSpotColor") && (x = xvalues[$.inArray(this.minyorg, yvalues)], target.drawCircle(canvasLeft + Math.round((x - this.minx) * (canvasWidth / rangex)), canvasTop + Math.round(canvasHeight - canvasHeight * ((this.minyorg - this.miny) / rangey)), spotRadius, undefined, options.get("minSpotColor")).append()), spotRadius && options.get("maxSpotColor") && (x = xvalues[$.inArray(this.maxyorg, yvalues)], target.drawCircle(canvasLeft + Math.round((x - this.minx) * (canvasWidth / rangex)), canvasTop + Math.round(canvasHeight - canvasHeight * ((this.maxyorg - this.miny) / rangey)), spotRadius, undefined, options.get("maxSpotColor")).append())), this.lastShapeId = target.getLastShapeId(), this.canvasTop = canvasTop, target.render()
                }
            }
        }), $.fn.sparkline.bar = bar = createClass($.fn.sparkline._base, barHighlightMixin, {
            type: "bar", init: function (el, values, options, width, height) {
                var isStackString, groupMin, groupMax, stackRanges, numValues, i, vlen, range, zeroAxis, xaxisOffset,
                    min, max, clipMin, clipMax, stacked, vlist, j, slen, svals, val, yoffset, yMaxCalc,
                    barWidth = parseInt(options.get("barWidth"), 10),
                    barSpacing = parseInt(options.get("barSpacing"), 10), chartRangeMin = options.get("chartRangeMin"),
                    chartRangeMax = options.get("chartRangeMax"), chartRangeClip = options.get("chartRangeClip"),
                    stackMin = 1 / 0, stackMax = -1 / 0;
                for (bar._super.init.call(this, el, values, options, width, height), i = 0, vlen = values.length; vlen > i; i++) val = values[i], isStackString = "string" == typeof val && val.indexOf(":") > -1, (isStackString || $.isArray(val)) && (stacked = !0, isStackString && (val = values[i] = normalizeValues(val.split(":"))), val = remove(val, null), groupMin = Math.min.apply(Math, val), groupMax = Math.max.apply(Math, val), stackMin > groupMin && (stackMin = groupMin), groupMax > stackMax && (stackMax = groupMax));
                this.stacked = stacked, this.regionShapes = {}, this.barWidth = barWidth, this.barSpacing = barSpacing, this.totalBarWidth = barWidth + barSpacing, this.width = width = values.length * barWidth + (values.length - 1) * barSpacing, this.initTarget(), chartRangeClip && (clipMin = chartRangeMin === undefined ? -1 / 0 : chartRangeMin, clipMax = chartRangeMax === undefined ? 1 / 0 : chartRangeMax), numValues = [], stackRanges = stacked ? [] : numValues;
                var stackTotals = [], stackRangesNeg = [];
                for (i = 0, vlen = values.length; vlen > i; i++) if (stacked) for (vlist = values[i], values[i] = svals = [], stackTotals[i] = 0, stackRanges[i] = stackRangesNeg[i] = 0, j = 0, slen = vlist.length; slen > j; j++) val = svals[j] = chartRangeClip ? clipval(vlist[j], clipMin, clipMax) : vlist[j], null !== val && (val > 0 && (stackTotals[i] += val), 0 > stackMin && stackMax > 0 ? 0 > val ? stackRangesNeg[i] += Math.abs(val) : stackRanges[i] += val : stackRanges[i] += Math.abs(val - (0 > val ? stackMax : stackMin)), numValues.push(val));
                else val = chartRangeClip ? clipval(values[i], clipMin, clipMax) : values[i], val = values[i] = normalizeValue(val), null !== val && numValues.push(val);
                this.max = max = Math.max.apply(Math, numValues), this.min = min = Math.min.apply(Math, numValues), this.stackMax = stackMax = stacked ? Math.max.apply(Math, stackTotals) : max, this.stackMin = stackMin = stacked ? Math.min.apply(Math, numValues) : min, options.get("chartRangeMin") !== undefined && (options.get("chartRangeClip") || options.get("chartRangeMin") < min) && (min = options.get("chartRangeMin")), options.get("chartRangeMax") !== undefined && (options.get("chartRangeClip") || options.get("chartRangeMax") > max) && (max = options.get("chartRangeMax")), this.zeroAxis = zeroAxis = options.get("zeroAxis", !0), xaxisOffset = 0 >= min && max >= 0 && zeroAxis ? 0 : 0 == zeroAxis ? min : min > 0 ? min : max, this.xaxisOffset = xaxisOffset, range = stacked ? Math.max.apply(Math, stackRanges) + Math.max.apply(Math, stackRangesNeg) : max - min, this.canvasHeightEf = zeroAxis && 0 > min ? this.canvasHeight - 2 : this.canvasHeight - 1, xaxisOffset > min ? (yMaxCalc = stacked && max >= 0 ? stackMax : max, yoffset = (yMaxCalc - xaxisOffset) / range * this.canvasHeight, yoffset !== Math.ceil(yoffset) && (this.canvasHeightEf -= 2, yoffset = Math.ceil(yoffset))) : yoffset = this.canvasHeight, this.yoffset = yoffset, $.isArray(options.get("colorMap")) ? (this.colorMapByIndex = options.get("colorMap"), this.colorMapByValue = null) : (this.colorMapByIndex = null, this.colorMapByValue = options.get("colorMap"), this.colorMapByValue && this.colorMapByValue.get === undefined && (this.colorMapByValue = new RangeMap(this.colorMapByValue))), this.range = range
            }, getRegion: function (el, x) {
                var result = Math.floor(x / this.totalBarWidth);
                return 0 > result || result >= this.values.length ? undefined : result
            }, getCurrentRegionFields: function () {
                var value, i, currentRegion = this.currentRegion, values = ensureArray(this.values[currentRegion]),
                    result = [];
                for (i = values.length; i--;) value = values[i], result.push({
                    isNull: null === value,
                    value: value,
                    color: this.calcColor(i, value, currentRegion),
                    offset: currentRegion
                });
                return result
            }, calcColor: function (stacknum, value, valuenum) {
                var color, newColor, colorMapByIndex = this.colorMapByIndex, colorMapByValue = this.colorMapByValue,
                    options = this.options;
                return color = this.stacked ? options.get("stackedBarColor") : 0 > value ? options.get("negBarColor") : options.get("barColor"), 0 === value && options.get("zeroColor") !== undefined && (color = options.get("zeroColor")), colorMapByValue && (newColor = colorMapByValue.get(value)) ? color = newColor : colorMapByIndex && colorMapByIndex.length > valuenum && (color = colorMapByIndex[valuenum]), $.isArray(color) ? color[stacknum % color.length] : color
            }, renderRegion: function (valuenum, highlight) {
                var y, height, color, isNull, yoffsetNeg, i, valcount, val, minPlotted, allMin,
                    vals = this.values[valuenum], options = this.options, xaxisOffset = this.xaxisOffset, result = [],
                    range = this.range, stacked = this.stacked, target = this.target, x = valuenum * this.totalBarWidth,
                    canvasHeightEf = this.canvasHeightEf, yoffset = this.yoffset;
                if (vals = $.isArray(vals) ? vals : [vals], valcount = vals.length, val = vals[0], isNull = all(null, vals), allMin = all(xaxisOffset, vals, !0), isNull) return options.get("nullColor") ? (color = highlight ? options.get("nullColor") : this.calcHighlightColor(options.get("nullColor"), options), y = yoffset > 0 ? yoffset - 1 : yoffset, target.drawRect(x, y, this.barWidth - 1, 0, color, color)) : undefined;
                for (yoffsetNeg = yoffset, i = 0; valcount > i; i++) {
                    if (val = vals[i], stacked && val === xaxisOffset) {
                        if (!allMin || minPlotted) continue;
                        minPlotted = !0
                    }
                    height = range > 0 ? Math.floor(canvasHeightEf * (Math.abs(val - xaxisOffset) / range)) + 1 : 1, xaxisOffset > val || val === xaxisOffset && 0 === yoffset ? (y = yoffsetNeg, yoffsetNeg += height) : (y = yoffset - height, yoffset -= height), color = this.calcColor(i, val, valuenum), highlight && (color = this.calcHighlightColor(color, options)), result.push(target.drawRect(x, y, this.barWidth - 1, height - 1, color, color))
                }
                return 1 === result.length ? result[0] : result
            }
        }), $.fn.sparkline.tristate = tristate = createClass($.fn.sparkline._base, barHighlightMixin, {
            type: "tristate", init: function (el, values, options, width, height) {
                var barWidth = parseInt(options.get("barWidth"), 10),
                    barSpacing = parseInt(options.get("barSpacing"), 10);
                tristate._super.init.call(this, el, values, options, width, height), this.regionShapes = {}, this.barWidth = barWidth, this.barSpacing = barSpacing, this.totalBarWidth = barWidth + barSpacing, this.values = $.map(values, Number), this.width = width = values.length * barWidth + (values.length - 1) * barSpacing, $.isArray(options.get("colorMap")) ? (this.colorMapByIndex = options.get("colorMap"), this.colorMapByValue = null) : (this.colorMapByIndex = null, this.colorMapByValue = options.get("colorMap"), this.colorMapByValue && this.colorMapByValue.get === undefined && (this.colorMapByValue = new RangeMap(this.colorMapByValue))), this.initTarget()
            }, getRegion: function (el, x) {
                return Math.floor(x / this.totalBarWidth)
            }, getCurrentRegionFields: function () {
                var currentRegion = this.currentRegion;
                return {
                    isNull: this.values[currentRegion] === undefined,
                    value: this.values[currentRegion],
                    color: this.calcColor(this.values[currentRegion], currentRegion),
                    offset: currentRegion
                }
            }, calcColor: function (value, valuenum) {
                var color, newColor, values = this.values, options = this.options,
                    colorMapByIndex = this.colorMapByIndex, colorMapByValue = this.colorMapByValue;
                return color = colorMapByValue && (newColor = colorMapByValue.get(value)) ? newColor : colorMapByIndex && colorMapByIndex.length > valuenum ? colorMapByIndex[valuenum] : values[valuenum] < 0 ? options.get("negBarColor") : values[valuenum] > 0 ? options.get("posBarColor") : options.get("zeroBarColor")
            }, renderRegion: function (valuenum, highlight) {
                var canvasHeight, height, halfHeight, x, y, color, values = this.values, options = this.options,
                    target = this.target;
                return canvasHeight = target.pixelHeight, halfHeight = Math.round(canvasHeight / 2), x = valuenum * this.totalBarWidth, values[valuenum] < 0 ? (y = halfHeight, height = halfHeight - 1) : values[valuenum] > 0 ? (y = 0, height = halfHeight - 1) : (y = halfHeight - 1, height = 2), color = this.calcColor(values[valuenum], valuenum), null !== color ? (highlight && (color = this.calcHighlightColor(color, options)), target.drawRect(x, y, this.barWidth - 1, height - 1, color, color)) : void 0
            }
        }), $.fn.sparkline.discrete = discrete = createClass($.fn.sparkline._base, barHighlightMixin, {
            type: "discrete", init: function (el, values, options, width, height) {
                discrete._super.init.call(this, el, values, options, width, height), this.regionShapes = {}, this.values = values = $.map(values, Number), this.min = Math.min.apply(Math, values), this.max = Math.max.apply(Math, values), this.range = this.max - this.min, this.width = width = "auto" === options.get("width") ? 2 * values.length : this.width, this.interval = Math.floor(width / values.length), this.itemWidth = width / values.length, options.get("chartRangeMin") !== undefined && (options.get("chartRangeClip") || options.get("chartRangeMin") < this.min) && (this.min = options.get("chartRangeMin")), options.get("chartRangeMax") !== undefined && (options.get("chartRangeClip") || options.get("chartRangeMax") > this.max) && (this.max = options.get("chartRangeMax")), this.initTarget(), this.target && (this.lineHeight = "auto" === options.get("lineHeight") ? Math.round(.3 * this.canvasHeight) : options.get("lineHeight"))
            }, getRegion: function (el, x) {
                return Math.floor(x / this.itemWidth)
            }, getCurrentRegionFields: function () {
                var currentRegion = this.currentRegion;
                return {
                    isNull: this.values[currentRegion] === undefined,
                    value: this.values[currentRegion],
                    offset: currentRegion
                }
            }, renderRegion: function (valuenum, highlight) {
                var ytop, val, color, x, values = this.values, options = this.options, min = this.min, max = this.max,
                    range = this.range, interval = this.interval, target = this.target,
                    canvasHeight = this.canvasHeight, lineHeight = this.lineHeight, pheight = canvasHeight - lineHeight;
                return val = clipval(values[valuenum], min, max), x = valuenum * interval, ytop = Math.round(pheight - pheight * ((val - min) / range)), color = options.get("thresholdColor") && val < options.get("thresholdValue") ? options.get("thresholdColor") : options.get("lineColor"), highlight && (color = this.calcHighlightColor(color, options)), target.drawLine(x, ytop, x, ytop + lineHeight, color)
            }
        }), $.fn.sparkline.bullet = bullet = createClass($.fn.sparkline._base, {
            type: "bullet", init: function (el, values, options, width, height) {
                var min, max, vals;
                bullet._super.init.call(this, el, values, options, width, height), this.values = values = normalizeValues(values), vals = values.slice(), vals[0] = null === vals[0] ? vals[2] : vals[0], vals[1] = null === values[1] ? vals[2] : vals[1], min = Math.min.apply(Math, values), max = Math.max.apply(Math, values), min = options.get("base") === undefined ? 0 > min ? min : 0 : options.get("base"), this.min = min, this.max = max, this.range = max - min, this.shapes = {}, this.valueShapes = {}, this.regiondata = {}, this.width = width = "auto" === options.get("width") ? "4.0em" : width, this.target = this.$el.simpledraw(width, height, options.get("composite")), values.length || (this.disabled = !0), this.initTarget()
            }, getRegion: function (el, x, y) {
                var shapeid = this.target.getShapeAt(el, x, y);
                return shapeid !== undefined && this.shapes[shapeid] !== undefined ? this.shapes[shapeid] : undefined
            }, getCurrentRegionFields: function () {
                var currentRegion = this.currentRegion;
                return {
                    fieldkey: currentRegion.substr(0, 1),
                    value: this.values[currentRegion.substr(1)],
                    region: currentRegion
                }
            }, changeHighlight: function (highlight) {
                var shape, currentRegion = this.currentRegion, shapeid = this.valueShapes[currentRegion];
                switch (delete this.shapes[shapeid], currentRegion.substr(0, 1)) {
                    case"r":
                        shape = this.renderRange(currentRegion.substr(1), highlight);
                        break;
                    case"p":
                        shape = this.renderPerformance(highlight);
                        break;
                    case"t":
                        shape = this.renderTarget(highlight)
                }
                this.valueShapes[currentRegion] = shape.id, this.shapes[shape.id] = currentRegion, this.target.replaceWithShape(shapeid, shape)
            }, renderRange: function (rn, highlight) {
                var rangeval = this.values[rn],
                    rangewidth = Math.round(this.canvasWidth * ((rangeval - this.min) / this.range)),
                    color = this.options.get("rangeColors")[rn - 2];
                return highlight && (color = this.calcHighlightColor(color, this.options)), this.target.drawRect(0, 0, rangewidth - 1, this.canvasHeight - 1, color, color)
            }, renderPerformance: function (highlight) {
                var perfval = this.values[1],
                    perfwidth = Math.round(this.canvasWidth * ((perfval - this.min) / this.range)),
                    color = this.options.get("performanceColor");
                return highlight && (color = this.calcHighlightColor(color, this.options)), this.target.drawRect(0, Math.round(.3 * this.canvasHeight), perfwidth - 1, Math.round(.4 * this.canvasHeight) - 1, color, color)
            }, renderTarget: function (highlight) {
                var targetval = this.values[0],
                    x = Math.round(this.canvasWidth * ((targetval - this.min) / this.range) - this.options.get("targetWidth") / 2),
                    targettop = Math.round(.1 * this.canvasHeight), targetheight = this.canvasHeight - 2 * targettop,
                    color = this.options.get("targetColor");
                return highlight && (color = this.calcHighlightColor(color, this.options)), this.target.drawRect(x, targettop, this.options.get("targetWidth") - 1, targetheight - 1, color, color)
            }, render: function () {
                var i, shape, vlen = this.values.length, target = this.target;
                if (bullet._super.render.call(this)) {
                    for (i = 2; vlen > i; i++) shape = this.renderRange(i).append(), this.shapes[shape.id] = "r" + i, this.valueShapes["r" + i] = shape.id;
                    null !== this.values[1] && (shape = this.renderPerformance().append(), this.shapes[shape.id] = "p1", this.valueShapes.p1 = shape.id), null !== this.values[0] && (shape = this.renderTarget().append(), this.shapes[shape.id] = "t0", this.valueShapes.t0 = shape.id), target.render()
                }
            }
        }), $.fn.sparkline.pie = pie = createClass($.fn.sparkline._base, {
            type: "pie", init: function (el, values, options, width, height) {
                var i, total = 0;
                if (pie._super.init.call(this, el, values, options, width, height), this.shapes = {}, this.valueShapes = {}, this.values = values = $.map(values, Number), "auto" === options.get("width") && (this.width = this.height), values.length > 0) for (i = values.length; i--;) total += values[i];
                this.total = total, this.initTarget(), this.radius = Math.floor(Math.min(this.canvasWidth, this.canvasHeight) / 2)
            }, getRegion: function (el, x, y) {
                var shapeid = this.target.getShapeAt(el, x, y);
                return shapeid !== undefined && this.shapes[shapeid] !== undefined ? this.shapes[shapeid] : undefined
            }, getCurrentRegionFields: function () {
                var currentRegion = this.currentRegion;
                return {
                    isNull: this.values[currentRegion] === undefined,
                    value: this.values[currentRegion],
                    percent: this.values[currentRegion] / this.total * 100,
                    color: this.options.get("sliceColors")[currentRegion % this.options.get("sliceColors").length],
                    offset: currentRegion
                }
            }, changeHighlight: function (highlight) {
                var currentRegion = this.currentRegion, newslice = this.renderSlice(currentRegion, highlight),
                    shapeid = this.valueShapes[currentRegion];
                delete this.shapes[shapeid], this.target.replaceWithShape(shapeid, newslice), this.valueShapes[currentRegion] = newslice.id, this.shapes[newslice.id] = currentRegion
            }, renderSlice: function (valuenum, highlight) {
                var start, end, i, vlen, color, target = this.target, options = this.options, radius = this.radius,
                    borderWidth = options.get("borderWidth"), offset = options.get("offset"), circle = 2 * Math.PI,
                    values = this.values, total = this.total, next = offset ? 2 * Math.PI * (offset / 360) : 0;
                for (vlen = values.length, i = 0; vlen > i; i++) {
                    if (start = next, end = next, total > 0 && (end = next + circle * (values[i] / total)), valuenum === i) return color = options.get("sliceColors")[i % options.get("sliceColors").length], highlight && (color = this.calcHighlightColor(color, options)), target.drawPieSlice(radius, radius, radius - borderWidth, start, end, undefined, color);
                    next = end
                }
            }, render: function () {
                var shape, i, target = this.target, values = this.values, options = this.options, radius = this.radius,
                    borderWidth = options.get("borderWidth");
                if (pie._super.render.call(this)) {
                    for (borderWidth && target.drawCircle(radius, radius, Math.floor(radius - borderWidth / 2), options.get("borderColor"), undefined, borderWidth).append(), i = values.length; i--;) values[i] && (shape = this.renderSlice(i).append(), this.valueShapes[i] = shape.id, this.shapes[shape.id] = i);
                    target.render()
                }
            }
        }), $.fn.sparkline.box = box = createClass($.fn.sparkline._base, {
            type: "box", init: function (el, values, options, width, height) {
                box._super.init.call(this, el, values, options, width, height), this.values = $.map(values, Number), this.width = "auto" === options.get("width") ? "4.0em" : width, this.initTarget(), this.values.length || (this.disabled = 1)
            }, getRegion: function () {
                return 1
            }, getCurrentRegionFields: function () {
                var result = [{field: "lq", value: this.quartiles[0]}, {
                    field: "med",
                    value: this.quartiles[1]
                }, {field: "uq", value: this.quartiles[2]}];
                return this.loutlier !== undefined && result.push({
                    field: "lo",
                    value: this.loutlier
                }), this.routlier !== undefined && result.push({
                    field: "ro",
                    value: this.routlier
                }), this.lwhisker !== undefined && result.push({
                    field: "lw",
                    value: this.lwhisker
                }), this.rwhisker !== undefined && result.push({field: "rw", value: this.rwhisker}), result
            }, render: function () {
                var lwhisker, loutlier, iqr, q1, q2, q3, rwhisker, routlier, i, size, unitSize, target = this.target,
                    values = this.values, vlen = values.length, options = this.options, canvasWidth = this.canvasWidth,
                    canvasHeight = this.canvasHeight,
                    minValue = options.get("chartRangeMin") === undefined ? Math.min.apply(Math, values) : options.get("chartRangeMin"),
                    maxValue = options.get("chartRangeMax") === undefined ? Math.max.apply(Math, values) : options.get("chartRangeMax"),
                    canvasLeft = 0;
                if (box._super.render.call(this)) {
                    if (options.get("raw")) options.get("showOutliers") && values.length > 5 ? (loutlier = values[0], lwhisker = values[1], q1 = values[2], q2 = values[3], q3 = values[4], rwhisker = values[5], routlier = values[6]) : (lwhisker = values[0], q1 = values[1], q2 = values[2], q3 = values[3], rwhisker = values[4]); else if (values.sort(function (a, b) {
                        return a - b
                    }), q1 = quartile(values, 1), q2 = quartile(values, 2), q3 = quartile(values, 3), iqr = q3 - q1, options.get("showOutliers")) {
                        for (lwhisker = rwhisker = undefined, i = 0; vlen > i; i++) lwhisker === undefined && values[i] > q1 - iqr * options.get("outlierIQR") && (lwhisker = values[i]), values[i] < q3 + iqr * options.get("outlierIQR") && (rwhisker = values[i]);
                        loutlier = values[0], routlier = values[vlen - 1]
                    } else lwhisker = values[0], rwhisker = values[vlen - 1];
                    this.quartiles = [q1, q2, q3], this.lwhisker = lwhisker, this.rwhisker = rwhisker, this.loutlier = loutlier, this.routlier = routlier, unitSize = canvasWidth / (maxValue - minValue + 1), options.get("showOutliers") && (canvasLeft = Math.ceil(options.get("spotRadius")), canvasWidth -= 2 * Math.ceil(options.get("spotRadius")), unitSize = canvasWidth / (maxValue - minValue + 1), lwhisker > loutlier && target.drawCircle((loutlier - minValue) * unitSize + canvasLeft, canvasHeight / 2, options.get("spotRadius"), options.get("outlierLineColor"), options.get("outlierFillColor")).append(), routlier > rwhisker && target.drawCircle((routlier - minValue) * unitSize + canvasLeft, canvasHeight / 2, options.get("spotRadius"), options.get("outlierLineColor"), options.get("outlierFillColor")).append()), target.drawRect(Math.round((q1 - minValue) * unitSize + canvasLeft), Math.round(.1 * canvasHeight), Math.round((q3 - q1) * unitSize), Math.round(.8 * canvasHeight), options.get("boxLineColor"), options.get("boxFillColor")).append(), target.drawLine(Math.round((lwhisker - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 2), Math.round((q1 - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 2), options.get("lineColor")).append(), target.drawLine(Math.round((lwhisker - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 4), Math.round((lwhisker - minValue) * unitSize + canvasLeft), Math.round(canvasHeight - canvasHeight / 4), options.get("whiskerColor")).append(), target.drawLine(Math.round((rwhisker - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 2), Math.round((q3 - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 2), options.get("lineColor")).append(), target.drawLine(Math.round((rwhisker - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 4), Math.round((rwhisker - minValue) * unitSize + canvasLeft), Math.round(canvasHeight - canvasHeight / 4), options.get("whiskerColor")).append(), target.drawLine(Math.round((q2 - minValue) * unitSize + canvasLeft), Math.round(.1 * canvasHeight), Math.round((q2 - minValue) * unitSize + canvasLeft), Math.round(.9 * canvasHeight), options.get("medianColor")).append(), options.get("target") && (size = Math.ceil(options.get("spotRadius")), target.drawLine(Math.round((options.get("target") - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 2 - size), Math.round((options.get("target") - minValue) * unitSize + canvasLeft), Math.round(canvasHeight / 2 + size), options.get("targetColor")).append(), target.drawLine(Math.round((options.get("target") - minValue) * unitSize + canvasLeft - size), Math.round(canvasHeight / 2), Math.round((options.get("target") - minValue) * unitSize + canvasLeft + size), Math.round(canvasHeight / 2), options.get("targetColor")).append()), target.render()
                }
            }
        }), VShape = createClass({
            init: function (target, id, type, args) {
                this.target = target, this.id = id, this.type = type, this.args = args
            }, append: function () {
                return this.target.appendShape(this), this
            }
        }), VCanvas_base = createClass({
            _pxregex: /(\d+)(px)?\s*$/i, init: function (width, height, target) {
                width && (this.width = width, this.height = height, this.target = target, this.lastShapeId = null, target[0] && (target = target[0]), $.data(target, "_jqs_vcanvas", this))
            }, drawLine: function (x1, y1, x2, y2, lineColor, lineWidth) {
                return this.drawShape([[x1, y1], [x2, y2]], lineColor, lineWidth)
            }, drawShape: function (path, lineColor, fillColor, lineWidth) {
                return this._genShape("Shape", [path, lineColor, fillColor, lineWidth])
            }, drawCircle: function (x, y, radius, lineColor, fillColor, lineWidth) {
                return this._genShape("Circle", [x, y, radius, lineColor, fillColor, lineWidth])
            }, drawPieSlice: function (x, y, radius, startAngle, endAngle, lineColor, fillColor) {
                return this._genShape("PieSlice", [x, y, radius, startAngle, endAngle, lineColor, fillColor])
            }, drawRect: function (x, y, width, height, lineColor, fillColor) {
                return this._genShape("Rect", [x, y, width, height, lineColor, fillColor])
            }, getElement: function () {
                return this.canvas
            }, getLastShapeId: function () {
                return this.lastShapeId
            }, reset: function () {
                alert("reset not implemented")
            }, _insert: function (el, target) {
                $(target).html(el)
            }, _calculatePixelDims: function (width, height, canvas) {
                var match;
                match = this._pxregex.exec(height), this.pixelHeight = match ? match[1] : $(canvas).height(), match = this._pxregex.exec(width), this.pixelWidth = match ? match[1] : $(canvas).width()
            }, _genShape: function (shapetype, shapeargs) {
                var id = shapeCount++;
                return shapeargs.unshift(id), new VShape(this, id, shapetype, shapeargs)
            }, appendShape: function () {
                alert("appendShape not implemented")
            }, replaceWithShape: function () {
                alert("replaceWithShape not implemented")
            }, insertAfterShape: function () {
                alert("insertAfterShape not implemented")
            }, removeShapeId: function () {
                alert("removeShapeId not implemented")
            }, getShapeAt: function () {
                alert("getShapeAt not implemented")
            }, render: function () {
                alert("render not implemented")
            }
        }), VCanvas_canvas = createClass(VCanvas_base, {
            init: function (width, height, target, interact) {
                VCanvas_canvas._super.init.call(this, width, height, target), this.canvas = document.createElement("canvas"), target[0] && (target = target[0]), $.data(target, "_jqs_vcanvas", this), $(this.canvas).css({
                    display: "inline-block",
                    width: width,
                    height: height,
                    verticalAlign: "top"
                }), this._insert(this.canvas, target), this._calculatePixelDims(width, height, this.canvas), this.canvas.width = this.pixelWidth, this.canvas.height = this.pixelHeight, this.interact = interact, this.shapes = {}, this.shapeseq = [], this.currentTargetShapeId = undefined, $(this.canvas).css({
                    width: this.pixelWidth,
                    height: this.pixelHeight
                })
            }, _getContext: function (lineColor, fillColor, lineWidth) {
                var context = this.canvas.getContext("2d");
                return lineColor !== undefined && (context.strokeStyle = lineColor), context.lineWidth = lineWidth === undefined ? 1 : lineWidth, fillColor !== undefined && (context.fillStyle = fillColor), context
            }, reset: function () {
                var context = this._getContext();
                context.clearRect(0, 0, this.pixelWidth, this.pixelHeight), this.shapes = {}, this.shapeseq = [], this.currentTargetShapeId = undefined
            }, _drawShape: function (shapeid, path, lineColor, fillColor, lineWidth) {
                var i, plen, context = this._getContext(lineColor, fillColor, lineWidth);
                for (context.beginPath(), context.moveTo(path[0][0] + .5, path[0][1] + .5), i = 1, plen = path.length; plen > i; i++) context.lineTo(path[i][0] + .5, path[i][1] + .5);
                lineColor !== undefined && context.stroke(), fillColor !== undefined && context.fill(), this.targetX !== undefined && this.targetY !== undefined && context.isPointInPath(this.targetX, this.targetY) && (this.currentTargetShapeId = shapeid)
            }, _drawCircle: function (shapeid, x, y, radius, lineColor, fillColor, lineWidth) {
                var context = this._getContext(lineColor, fillColor, lineWidth);
                context.beginPath(), context.arc(x, y, radius, 0, 2 * Math.PI, !1), this.targetX !== undefined && this.targetY !== undefined && context.isPointInPath(this.targetX, this.targetY) && (this.currentTargetShapeId = shapeid), lineColor !== undefined && context.stroke(), fillColor !== undefined && context.fill()
            }, _drawPieSlice: function (shapeid, x, y, radius, startAngle, endAngle, lineColor, fillColor) {
                var context = this._getContext(lineColor, fillColor);
                context.beginPath(), context.moveTo(x, y), context.arc(x, y, radius, startAngle, endAngle, !1), context.lineTo(x, y), context.closePath(), lineColor !== undefined && context.stroke(), fillColor && context.fill(), this.targetX !== undefined && this.targetY !== undefined && context.isPointInPath(this.targetX, this.targetY) && (this.currentTargetShapeId = shapeid)
            }, _drawRect: function (shapeid, x, y, width, height, lineColor, fillColor) {
                return this._drawShape(shapeid, [[x, y], [x + width, y], [x + width, y + height], [x, y + height], [x, y]], lineColor, fillColor)
            }, appendShape: function (shape) {
                return this.shapes[shape.id] = shape, this.shapeseq.push(shape.id), this.lastShapeId = shape.id, shape.id
            }, replaceWithShape: function (shapeid, shape) {
                var i, shapeseq = this.shapeseq;
                for (this.shapes[shape.id] = shape, i = shapeseq.length; i--;) shapeseq[i] == shapeid && (shapeseq[i] = shape.id);
                delete this.shapes[shapeid]
            }, replaceWithShapes: function (shapeids, shapes) {
                var sid, i, first, shapeseq = this.shapeseq, shapemap = {};
                for (i = shapeids.length; i--;) shapemap[shapeids[i]] = !0;
                for (i = shapeseq.length; i--;) sid = shapeseq[i], shapemap[sid] && (shapeseq.splice(i, 1), delete this.shapes[sid], first = i);
                for (i = shapes.length; i--;) shapeseq.splice(first, 0, shapes[i].id), this.shapes[shapes[i].id] = shapes[i]
            }, insertAfterShape: function (shapeid, shape) {
                var i, shapeseq = this.shapeseq;
                for (i = shapeseq.length; i--;) if (shapeseq[i] === shapeid) return shapeseq.splice(i + 1, 0, shape.id), this.shapes[shape.id] = shape, void 0
            }, removeShapeId: function (shapeid) {
                var i, shapeseq = this.shapeseq;
                for (i = shapeseq.length; i--;) if (shapeseq[i] === shapeid) {
                    shapeseq.splice(i, 1);
                    break
                }
                delete this.shapes[shapeid]
            }, getShapeAt: function (el, x, y) {
                return this.targetX = x, this.targetY = y, this.render(), this.currentTargetShapeId
            }, render: function () {
                var shapeid, shape, i, shapeseq = this.shapeseq, shapes = this.shapes, shapeCount = shapeseq.length,
                    context = this._getContext();
                for (context.clearRect(0, 0, this.pixelWidth, this.pixelHeight), i = 0; shapeCount > i; i++) shapeid = shapeseq[i], shape = shapes[shapeid], this["_draw" + shape.type].apply(this, shape.args);
                this.interact || (this.shapes = {}, this.shapeseq = [])
            }
        }), VCanvas_vml = createClass(VCanvas_base, {
            init: function (width, height, target) {
                var groupel;
                VCanvas_vml._super.init.call(this, width, height, target), target[0] && (target = target[0]), $.data(target, "_jqs_vcanvas", this), this.canvas = document.createElement("span"), $(this.canvas).css({
                    display: "inline-block",
                    position: "relative",
                    overflow: "hidden",
                    width: width,
                    height: height,
                    margin: "0px",
                    padding: "0px",
                    verticalAlign: "top"
                }), this._insert(this.canvas, target), this._calculatePixelDims(width, height, this.canvas), this.canvas.width = this.pixelWidth, this.canvas.height = this.pixelHeight, groupel = '<v:group coordorigin="0 0" coordsize="' + this.pixelWidth + " " + this.pixelHeight + '" style="position:absolute;top:0;left:0;width:' + this.pixelWidth + "px;height=" + this.pixelHeight + 'px;"></v:group>', this.canvas.insertAdjacentHTML("beforeEnd", groupel), this.group = $(this.canvas).children()[0], this.rendered = !1, this.prerender = ""
            }, _drawShape: function (shapeid, path, lineColor, fillColor, lineWidth) {
                var initial, stroke, fill, closed, vel, plen, i, vpath = [];
                for (i = 0, plen = path.length; plen > i; i++) vpath[i] = "" + path[i][0] + "," + path[i][1];
                return initial = vpath.splice(0, 1), lineWidth = lineWidth === undefined ? 1 : lineWidth, stroke = lineColor === undefined ? ' stroked="false" ' : ' strokeWeight="' + lineWidth + 'px" strokeColor="' + lineColor + '" ', fill = fillColor === undefined ? ' filled="false"' : ' fillColor="' + fillColor + '" filled="true" ', closed = vpath[0] === vpath[vpath.length - 1] ? "x " : "", vel = '<v:shape coordorigin="0 0" coordsize="' + this.pixelWidth + " " + this.pixelHeight + '"  id="jqsshape' + shapeid + '" ' + stroke + fill + ' style="position:absolute;left:0px;top:0px;height:' + this.pixelHeight + "px;width:" + this.pixelWidth + 'px;padding:0px;margin:0px;"  path="m ' + initial + " l " + vpath.join(", ") + " " + closed + 'e"> </v:shape>'
            }, _drawCircle: function (shapeid, x, y, radius, lineColor, fillColor, lineWidth) {
                var stroke, fill, vel;
                return x -= radius, y -= radius, stroke = lineColor === undefined ? ' stroked="false" ' : ' strokeWeight="' + lineWidth + 'px" strokeColor="' + lineColor + '" ', fill = fillColor === undefined ? ' filled="false"' : ' fillColor="' + fillColor + '" filled="true" ', vel = '<v:oval  id="jqsshape' + shapeid + '" ' + stroke + fill + ' style="position:absolute;top:' + y + "px; left:" + x + "px; width:" + 2 * radius + "px; height:" + 2 * radius + 'px"></v:oval>'
            }, _drawPieSlice: function (shapeid, x, y, radius, startAngle, endAngle, lineColor, fillColor) {
                var vpath, startx, starty, endx, endy, stroke, fill, vel;
                if (startAngle === endAngle) return "";
                if (endAngle - startAngle === 2 * Math.PI && (startAngle = 0, endAngle = 2 * Math.PI), startx = x + Math.round(Math.cos(startAngle) * radius), starty = y + Math.round(Math.sin(startAngle) * radius), endx = x + Math.round(Math.cos(endAngle) * radius), endy = y + Math.round(Math.sin(endAngle) * radius), startx === endx && starty === endy) {
                    if (endAngle - startAngle < Math.PI) return "";
                    startx = endx = x + radius, starty = endy = y
                }
                return startx === endx && starty === endy && endAngle - startAngle < Math.PI ? "" : (vpath = [x - radius, y - radius, x + radius, y + radius, startx, starty, endx, endy], stroke = lineColor === undefined ? ' stroked="false" ' : ' strokeWeight="1px" strokeColor="' + lineColor + '" ', fill = fillColor === undefined ? ' filled="false"' : ' fillColor="' + fillColor + '" filled="true" ', vel = '<v:shape coordorigin="0 0" coordsize="' + this.pixelWidth + " " + this.pixelHeight + '"  id="jqsshape' + shapeid + '" ' + stroke + fill + ' style="position:absolute;left:0px;top:0px;height:' + this.pixelHeight + "px;width:" + this.pixelWidth + 'px;padding:0px;margin:0px;"  path="m ' + x + "," + y + " wa " + vpath.join(", ") + ' x e"> </v:shape>')
            }, _drawRect: function (shapeid, x, y, width, height, lineColor, fillColor) {
                return this._drawShape(shapeid, [[x, y], [x, y + height], [x + width, y + height], [x + width, y], [x, y]], lineColor, fillColor)
            }, reset: function () {
                this.group.innerHTML = ""
            }, appendShape: function (shape) {
                var vel = this["_draw" + shape.type].apply(this, shape.args);
                return this.rendered ? this.group.insertAdjacentHTML("beforeEnd", vel) : this.prerender += vel, this.lastShapeId = shape.id, shape.id
            }, replaceWithShape: function (shapeid, shape) {
                var existing = $("#jqsshape" + shapeid), vel = this["_draw" + shape.type].apply(this, shape.args);
                existing[0].outerHTML = vel
            }, replaceWithShapes: function (shapeids, shapes) {
                var i, existing = $("#jqsshape" + shapeids[0]), replace = "", slen = shapes.length;
                for (i = 0; slen > i; i++) replace += this["_draw" + shapes[i].type].apply(this, shapes[i].args);
                for (existing[0].outerHTML = replace, i = 1; i < shapeids.length; i++) $("#jqsshape" + shapeids[i]).remove()
            }, insertAfterShape: function (shapeid, shape) {
                var existing = $("#jqsshape" + shapeid), vel = this["_draw" + shape.type].apply(this, shape.args);
                existing[0].insertAdjacentHTML("afterEnd", vel)
            }, removeShapeId: function (shapeid) {
                var existing = $("#jqsshape" + shapeid);
                this.group.removeChild(existing[0])
            }, getShapeAt: function (el) {
                var shapeid = el.id.substr(8);
                return shapeid
            }, render: function () {
                this.rendered || (this.group.innerHTML = this.prerender, this.rendered = !0)
            }
        })
    })
}(document, Math);