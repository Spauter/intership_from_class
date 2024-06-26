(function () {
    var $, Morris, minutesSpecHelper, secondsSpecHelper, __slice = [].slice, __bind = function (fn, me) {
        return function () {
            return fn.apply(me, arguments)
        }
    }, __hasProp = {}.hasOwnProperty, __extends = function (child, parent) {
        function ctor() {
            this.constructor = child
        }

        for (var key in parent) __hasProp.call(parent, key) && (child[key] = parent[key]);
        return ctor.prototype = parent.prototype, child.prototype = new ctor, child.__super__ = parent.prototype, child
    }, __indexOf = [].indexOf || function (item) {
        for (var i = 0, l = this.length; l > i; i++) if (i in this && this[i] === item) return i;
        return -1
    };
    Morris = window.Morris = {}, $ = jQuery, Morris.EventEmitter = function () {
        function EventEmitter() {
        }

        return EventEmitter.prototype.on = function (name, handler) {
            return null == this.handlers && (this.handlers = {}), null == this.handlers[name] && (this.handlers[name] = []), this.handlers[name].push(handler), this
        }, EventEmitter.prototype.fire = function () {
            var args, handler, name, _i, _len, _ref, _results;
            if (name = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [], null != this.handlers && null != this.handlers[name]) {
                for (_ref = this.handlers[name], _results = [], _i = 0, _len = _ref.length; _len > _i; _i++) handler = _ref[_i], _results.push(handler.apply(null, args));
                return _results
            }
        }, EventEmitter
    }(), Morris.commas = function (num) {
        var absnum, intnum, ret, strabsnum;
        return null != num ? (ret = 0 > num ? "-" : "", absnum = Math.abs(num), intnum = Math.floor(absnum).toFixed(0), ret += intnum.replace(/(?=(?:\d{3})+$)(?!^)/g, ","), strabsnum = absnum.toString(), strabsnum.length > intnum.length && (ret += strabsnum.slice(intnum.length)), ret) : "-"
    }, Morris.pad2 = function (number) {
        return (10 > number ? "0" : "") + number
    }, Morris.Grid = function (_super) {
        function Grid(options) {
            this.resizeHandler = __bind(this.resizeHandler, this);
            var _this = this;
            if (this.el = "string" == typeof options.element ? $(document.getElementById(options.element)) : $(options.element), null == this.el || 0 === this.el.length) throw new Error("Graph container element not found");
            "static" === this.el.css("position") && this.el.css("position", "relative"), this.options = $.extend({}, this.gridDefaults, this.defaults || {}, options), "string" == typeof this.options.units && (this.options.postUnits = options.units), this.raphael = new Raphael(this.el[0]), this.elementWidth = null, this.elementHeight = null, this.dirty = !1, this.selectFrom = null, this.init && this.init(), this.setData(this.options.data), this.el.bind("mousemove", function (evt) {
                var left, offset, right, width, x;
                return offset = _this.el.offset(), x = evt.pageX - offset.left, _this.selectFrom ? (left = _this.data[_this.hitTest(Math.min(x, _this.selectFrom))]._x, right = _this.data[_this.hitTest(Math.max(x, _this.selectFrom))]._x, width = right - left, _this.selectionRect.attr({
                    x: left,
                    width: width
                })) : _this.fire("hovermove", x, evt.pageY - offset.top)
            }), this.el.bind("mouseleave", function () {
                return _this.selectFrom && (_this.selectionRect.hide(), _this.selectFrom = null), _this.fire("hoverout")
            }), this.el.bind("touchstart touchmove touchend", function (evt) {
                var offset, touch;
                return touch = evt.originalEvent.touches[0] || evt.originalEvent.changedTouches[0], offset = _this.el.offset(), _this.fire("hover", touch.pageX - offset.left, touch.pageY - offset.top), touch
            }), this.el.bind("click", function (evt) {
                var offset;
                return offset = _this.el.offset(), _this.fire("gridclick", evt.pageX - offset.left, evt.pageY - offset.top)
            }), this.options.rangeSelect && (this.selectionRect = this.raphael.rect(0, 0, 0, this.el.innerHeight()).attr({
                fill: this.options.rangeSelectColor,
                stroke: !1
            }).toBack().hide(), this.el.bind("mousedown", function (evt) {
                var offset;
                return offset = _this.el.offset(), _this.startRange(evt.pageX - offset.left)
            }), this.el.bind("mouseup", function (evt) {
                var offset;
                return offset = _this.el.offset(), _this.endRange(evt.pageX - offset.left), _this.fire("hovermove", evt.pageX - offset.left, evt.pageY - offset.top)
            })), this.options.resize && $(window).bind("resize", function () {
                return null != _this.timeoutId && window.clearTimeout(_this.timeoutId), _this.timeoutId = window.setTimeout(_this.resizeHandler, 100)
            }), this.postInit && this.postInit()
        }

        return __extends(Grid, _super), Grid.prototype.gridDefaults = {
            dateFormat: null,
            axes: !0,
            grid: !0,
            gridLineColor: "#aaa",
            gridStrokeWidth: .5,
            gridTextColor: "#888",
            gridTextSize: 12,
            gridTextFamily: "sans-serif",
            gridTextWeight: "normal",
            hideHover: !1,
            yLabelFormat: null,
            xLabelAngle: 0,
            numLines: 5,
            padding: 25,
            parseTime: !0,
            postUnits: "",
            preUnits: "",
            ymax: "auto",
            ymin: "auto 0",
            goals: [],
            goalStrokeWidth: 1,
            goalLineColors: ["#666633", "#999966", "#cc6666", "#663333"],
            events: [],
            eventStrokeWidth: 1,
            eventLineColors: ["#005a04", "#ccffbb", "#3a5f0b", "#005502"],
            rangeSelect: null,
            rangeSelectColor: "#eef",
            resize: !1
        }, Grid.prototype.setData = function (data, redraw) {
            var e, idx, index, maxGoal, minGoal, ret, row, step, total, y, ykey, ymax, ymin, yval, _ref;
            return null == redraw && (redraw = !0), this.options.data = data, null == data || 0 === data.length ? (this.data = [], this.raphael.clear(), null != this.hover && this.hover.hide(), void 0) : (ymax = this.cumulative ? 0 : null, ymin = this.cumulative ? 0 : null, this.options.goals.length > 0 && (minGoal = Math.min.apply(Math, this.options.goals), maxGoal = Math.max.apply(Math, this.options.goals), ymin = null != ymin ? Math.min(ymin, minGoal) : minGoal, ymax = null != ymax ? Math.max(ymax, maxGoal) : maxGoal), this.data = function () {
                var _i, _len, _results;
                for (_results = [], index = _i = 0, _len = data.length; _len > _i; index = ++_i) row = data[index], ret = {src: row}, ret.label = row[this.options.xkey], this.options.parseTime ? (ret.x = Morris.parseDate(ret.label), this.options.dateFormat ? ret.label = this.options.dateFormat(ret.x) : "number" == typeof ret.label && (ret.label = new Date(ret.label).toString())) : (ret.x = index, this.options.xLabelFormat && (ret.label = this.options.xLabelFormat(ret))), total = 0, ret.y = function () {
                    var _j, _len1, _ref, _results1;
                    for (_ref = this.options.ykeys, _results1 = [], idx = _j = 0, _len1 = _ref.length; _len1 > _j; idx = ++_j) ykey = _ref[idx], yval = row[ykey], "string" == typeof yval && (yval = parseFloat(yval)), null != yval && "number" != typeof yval && (yval = null), null != yval && (this.cumulative ? total += yval : null != ymax ? (ymax = Math.max(yval, ymax), ymin = Math.min(yval, ymin)) : ymax = ymin = yval), this.cumulative && null != total && (ymax = Math.max(total, ymax), ymin = Math.min(total, ymin)), _results1.push(yval);
                    return _results1
                }.call(this), _results.push(ret);
                return _results
            }.call(this), this.options.parseTime && (this.data = this.data.sort(function (a, b) {
                return (a.x > b.x) - (b.x > a.x)
            })), this.xmin = this.data[0].x, this.xmax = this.data[this.data.length - 1].x, this.events = [], this.options.events.length > 0 && (this.events = this.options.parseTime ? function () {
                var _i, _len, _ref, _results;
                for (_ref = this.options.events, _results = [], _i = 0, _len = _ref.length; _len > _i; _i++) e = _ref[_i], _results.push(Morris.parseDate(e));
                return _results
            }.call(this) : this.options.events, this.xmax = Math.max(this.xmax, Math.max.apply(Math, this.events)), this.xmin = Math.min(this.xmin, Math.min.apply(Math, this.events))), this.xmin === this.xmax && (this.xmin -= 1, this.xmax += 1), this.ymin = this.yboundary("min", ymin), this.ymax = this.yboundary("max", ymax), this.ymin === this.ymax && (ymin && (this.ymin -= 1), this.ymax += 1), ((_ref = this.options.axes) === !0 || "both" === _ref || "y" === _ref || this.options.grid === !0) && (this.options.ymax === this.gridDefaults.ymax && this.options.ymin === this.gridDefaults.ymin ? (this.grid = this.autoGridLines(this.ymin, this.ymax, this.options.numLines), this.ymin = Math.min(this.ymin, this.grid[0]), this.ymax = Math.max(this.ymax, this.grid[this.grid.length - 1])) : (step = (this.ymax - this.ymin) / (this.options.numLines - 1), this.grid = function () {
                var _i, _ref1, _ref2, _results;
                for (_results = [], y = _i = _ref1 = this.ymin, _ref2 = this.ymax; step > 0 ? _ref2 >= _i : _i >= _ref2; y = _i += step) _results.push(y);
                return _results
            }.call(this))), this.dirty = !0, redraw ? this.redraw() : void 0)
        }, Grid.prototype.yboundary = function (boundaryType, currentValue) {
            var boundaryOption, suggestedValue;
            return boundaryOption = this.options["y" + boundaryType], "string" == typeof boundaryOption ? "auto" === boundaryOption.slice(0, 4) ? boundaryOption.length > 5 ? (suggestedValue = parseInt(boundaryOption.slice(5), 10), null == currentValue ? suggestedValue : Math[boundaryType](currentValue, suggestedValue)) : null != currentValue ? currentValue : 0 : parseInt(boundaryOption, 10) : boundaryOption
        }, Grid.prototype.autoGridLines = function (ymin, ymax, nlines) {
            var gmax, gmin, grid, smag, span, step, unit, y, ymag;
            return span = ymax - ymin, ymag = Math.floor(Math.log(span) / Math.log(10)), unit = Math.pow(10, ymag), gmin = Math.floor(ymin / unit) * unit, gmax = Math.ceil(ymax / unit) * unit, step = (gmax - gmin) / (nlines - 1), 1 === unit && step > 1 && Math.ceil(step) !== step && (step = Math.ceil(step), gmax = gmin + step * (nlines - 1)), 0 > gmin && gmax > 0 && (gmin = Math.floor(ymin / step) * step, gmax = Math.ceil(ymax / step) * step), 1 > step ? (smag = Math.floor(Math.log(step) / Math.log(10)), grid = function () {
                var _i, _results;
                for (_results = [], y = _i = gmin; step > 0 ? gmax >= _i : _i >= gmax; y = _i += step) _results.push(parseFloat(y.toFixed(1 - smag)));
                return _results
            }()) : grid = function () {
                var _i, _results;
                for (_results = [], y = _i = gmin; step > 0 ? gmax >= _i : _i >= gmax; y = _i += step) _results.push(y);
                return _results
            }(), grid
        }, Grid.prototype._calc = function () {
            var bottomOffsets, gridLine, h, i, w, yLabelWidths, _ref, _ref1;
            return w = this.el.width(), h = this.el.height(), (this.elementWidth !== w || this.elementHeight !== h || this.dirty) && (this.elementWidth = w, this.elementHeight = h, this.dirty = !1, this.left = this.options.padding, this.right = this.elementWidth - this.options.padding, this.top = this.options.padding, this.bottom = this.elementHeight - this.options.padding, ((_ref = this.options.axes) === !0 || "both" === _ref || "y" === _ref) && (yLabelWidths = function () {
                var _i, _len, _ref1, _results;
                for (_ref1 = this.grid, _results = [], _i = 0, _len = _ref1.length; _len > _i; _i++) gridLine = _ref1[_i], _results.push(this.measureText(this.yAxisFormat(gridLine)).width);
                return _results
            }.call(this), this.left += Math.max.apply(Math, yLabelWidths)), ((_ref1 = this.options.axes) === !0 || "both" === _ref1 || "x" === _ref1) && (bottomOffsets = function () {
                var _i, _ref2, _results;
                for (_results = [], i = _i = 0, _ref2 = this.data.length; _ref2 >= 0 ? _ref2 > _i : _i > _ref2; i = _ref2 >= 0 ? ++_i : --_i) _results.push(this.measureText(this.data[i].text, -this.options.xLabelAngle).height);
                return _results
            }.call(this), this.bottom -= Math.max.apply(Math, bottomOffsets)), this.width = Math.max(1, this.right - this.left), this.height = Math.max(1, this.bottom - this.top), this.dx = this.width / (this.xmax - this.xmin), this.dy = this.height / (this.ymax - this.ymin), this.calc) ? this.calc() : void 0
        }, Grid.prototype.transY = function (y) {
            return this.bottom - (y - this.ymin) * this.dy
        }, Grid.prototype.transX = function (x) {
            return 1 === this.data.length ? (this.left + this.right) / 2 : this.left + (x - this.xmin) * this.dx
        }, Grid.prototype.redraw = function () {
            return this.raphael.clear(), this._calc(), this.drawGrid(), this.drawGoals(), this.drawEvents(), this.draw ? this.draw() : void 0
        }, Grid.prototype.measureText = function (text, angle) {
            var ret, tt;
            return null == angle && (angle = 0), tt = this.raphael.text(100, 100, text).attr("font-size", this.options.gridTextSize).attr("font-family", this.options.gridTextFamily).attr("font-weight", this.options.gridTextWeight).rotate(angle), ret = tt.getBBox(), tt.remove(), ret
        }, Grid.prototype.yAxisFormat = function (label) {
            return this.yLabelFormat(label)
        }, Grid.prototype.yLabelFormat = function (label) {
            return "function" == typeof this.options.yLabelFormat ? this.options.yLabelFormat(label) : "" + this.options.preUnits + Morris.commas(label) + this.options.postUnits
        }, Grid.prototype.drawGrid = function () {
            var lineY, y, _i, _len, _ref, _ref1, _ref2, _results;
            if (this.options.grid !== !1 || (_ref = this.options.axes) === !0 || "both" === _ref || "y" === _ref) {
                for (_ref1 = this.grid, _results = [], _i = 0, _len = _ref1.length; _len > _i; _i++) lineY = _ref1[_i], y = this.transY(lineY), ((_ref2 = this.options.axes) === !0 || "both" === _ref2 || "y" === _ref2) && this.drawYAxisLabel(this.left - this.options.padding / 2, y, this.yAxisFormat(lineY)), this.options.grid ? _results.push(this.drawGridLine("M" + this.left + "," + y + "H" + (this.left + this.width))) : _results.push(void 0);
                return _results
            }
        }, Grid.prototype.drawGoals = function () {
            var color, goal, i, _i, _len, _ref, _results;
            for (_ref = this.options.goals, _results = [], i = _i = 0, _len = _ref.length; _len > _i; i = ++_i) goal = _ref[i], color = this.options.goalLineColors[i % this.options.goalLineColors.length], _results.push(this.drawGoal(goal, color));
            return _results
        }, Grid.prototype.drawEvents = function () {
            var color, event, i, _i, _len, _ref, _results;
            for (_ref = this.events, _results = [], i = _i = 0, _len = _ref.length; _len > _i; i = ++_i) event = _ref[i], color = this.options.eventLineColors[i % this.options.eventLineColors.length], _results.push(this.drawEvent(event, color));
            return _results
        }, Grid.prototype.drawGoal = function (goal, color) {
            return this.raphael.path("M" + this.left + "," + this.transY(goal) + "H" + this.right).attr("stroke", color).attr("stroke-width", this.options.goalStrokeWidth)
        }, Grid.prototype.drawEvent = function (event, color) {
            return this.raphael.path("M" + this.transX(event) + "," + this.bottom + "V" + this.top).attr("stroke", color).attr("stroke-width", this.options.eventStrokeWidth)
        }, Grid.prototype.drawYAxisLabel = function (xPos, yPos, text) {
            return this.raphael.text(xPos, yPos, text).attr("font-size", this.options.gridTextSize).attr("font-family", this.options.gridTextFamily).attr("font-weight", this.options.gridTextWeight).attr("fill", this.options.gridTextColor).attr("text-anchor", "end")
        }, Grid.prototype.drawGridLine = function (path) {
            return this.raphael.path(path).attr("stroke", this.options.gridLineColor).attr("stroke-width", this.options.gridStrokeWidth)
        }, Grid.prototype.startRange = function (x) {
            return this.hover.hide(), this.selectFrom = x, this.selectionRect.attr({x: x, width: 0}).show()
        }, Grid.prototype.endRange = function (x) {
            var end, start;
            return this.selectFrom ? (start = Math.min(this.selectFrom, x), end = Math.max(this.selectFrom, x), this.options.rangeSelect.call(this.el, {
                start: this.data[this.hitTest(start)].x,
                end: this.data[this.hitTest(end)].x
            }), this.selectFrom = null) : void 0
        }, Grid.prototype.resizeHandler = function () {
            return this.timeoutId = null, this.raphael.setSize(this.el.width(), this.el.height()), this.redraw()
        }, Grid
    }(Morris.EventEmitter), Morris.parseDate = function (date) {
        var isecs, m, msecs, n, o, offsetmins, p, q, r, ret, secs;
        return "number" == typeof date ? date : (m = date.match(/^(\d+) Q(\d)$/), n = date.match(/^(\d+)-(\d+)$/), o = date.match(/^(\d+)-(\d+)-(\d+)$/), p = date.match(/^(\d+) W(\d+)$/), q = date.match(/^(\d+)-(\d+)-(\d+)[ T](\d+):(\d+)(Z|([+-])(\d\d):?(\d\d))?$/), r = date.match(/^(\d+)-(\d+)-(\d+)[ T](\d+):(\d+):(\d+(\.\d+)?)(Z|([+-])(\d\d):?(\d\d))?$/), m ? new Date(parseInt(m[1], 10), 3 * parseInt(m[2], 10) - 1, 1).getTime() : n ? new Date(parseInt(n[1], 10), parseInt(n[2], 10) - 1, 1).getTime() : o ? new Date(parseInt(o[1], 10), parseInt(o[2], 10) - 1, parseInt(o[3], 10)).getTime() : p ? (ret = new Date(parseInt(p[1], 10), 0, 1), 4 !== ret.getDay() && ret.setMonth(0, 1 + (4 - ret.getDay() + 7) % 7), ret.getTime() + 6048e5 * parseInt(p[2], 10)) : q ? q[6] ? (offsetmins = 0, "Z" !== q[6] && (offsetmins = 60 * parseInt(q[8], 10) + parseInt(q[9], 10), "+" === q[7] && (offsetmins = 0 - offsetmins)), Date.UTC(parseInt(q[1], 10), parseInt(q[2], 10) - 1, parseInt(q[3], 10), parseInt(q[4], 10), parseInt(q[5], 10) + offsetmins)) : new Date(parseInt(q[1], 10), parseInt(q[2], 10) - 1, parseInt(q[3], 10), parseInt(q[4], 10), parseInt(q[5], 10)).getTime() : r ? (secs = parseFloat(r[6]), isecs = Math.floor(secs), msecs = Math.round(1e3 * (secs - isecs)), r[8] ? (offsetmins = 0, "Z" !== r[8] && (offsetmins = 60 * parseInt(r[10], 10) + parseInt(r[11], 10), "+" === r[9] && (offsetmins = 0 - offsetmins)), Date.UTC(parseInt(r[1], 10), parseInt(r[2], 10) - 1, parseInt(r[3], 10), parseInt(r[4], 10), parseInt(r[5], 10) + offsetmins, isecs, msecs)) : new Date(parseInt(r[1], 10), parseInt(r[2], 10) - 1, parseInt(r[3], 10), parseInt(r[4], 10), parseInt(r[5], 10), isecs, msecs).getTime()) : new Date(parseInt(date, 10), 0, 1).getTime())
    }, Morris.Hover = function () {
        function Hover(options) {
            null == options && (options = {}), this.options = $.extend({}, Morris.Hover.defaults, options), this.el = $("<div class='" + this.options["class"] + "'></div>"), this.el.hide(), this.options.parent.append(this.el)
        }

        return Hover.defaults = {"class": "morris-hover morris-default-style"}, Hover.prototype.update = function (html, x, y) {
            return this.html(html), this.show(), this.moveTo(x, y)
        }, Hover.prototype.html = function (content) {
            return this.el.html(content)
        }, Hover.prototype.moveTo = function (x, y) {
            var hoverHeight, hoverWidth, left, parentHeight, parentWidth, top;
            return parentWidth = this.options.parent.innerWidth(), parentHeight = this.options.parent.innerHeight(), hoverWidth = this.el.outerWidth(), hoverHeight = this.el.outerHeight(), left = Math.min(Math.max(0, x - hoverWidth / 2), parentWidth - hoverWidth), null != y ? (top = y - hoverHeight - 10, 0 > top && (top = y + 10, top + hoverHeight > parentHeight && (top = parentHeight / 2 - hoverHeight / 2))) : top = parentHeight / 2 - hoverHeight / 2, this.el.css({
                left: left + "px",
                top: parseInt(top) + "px"
            })
        }, Hover.prototype.show = function () {
            return this.el.show()
        }, Hover.prototype.hide = function () {
            return this.el.hide()
        }, Hover
    }(), Morris.Line = function (_super) {
        function Line(options) {
            return this.hilight = __bind(this.hilight, this), this.onHoverOut = __bind(this.onHoverOut, this), this.onHoverMove = __bind(this.onHoverMove, this), this.onGridClick = __bind(this.onGridClick, this), this instanceof Morris.Line ? (Line.__super__.constructor.call(this, options), void 0) : new Morris.Line(options)
        }

        return __extends(Line, _super), Line.prototype.init = function () {
            return "always" !== this.options.hideHover ? (this.hover = new Morris.Hover({parent: this.el}), this.on("hovermove", this.onHoverMove), this.on("hoverout", this.onHoverOut), this.on("gridclick", this.onGridClick)) : void 0
        }, Line.prototype.defaults = {
            lineWidth: 3,
            pointSize: 4,
            lineColors: ["#0b62a4", "#7A92A3", "#4da74d", "#afd8f8", "#edc240", "#cb4b4b", "#9440ed"],
            pointStrokeWidths: [1],
            pointStrokeColors: ["#ffffff"],
            pointFillColors: [],
            smooth: !0,
            xLabels: "auto",
            xLabelFormat: null,
            xLabelMargin: 24,
            continuousLine: !0,
            hideHover: !1
        }, Line.prototype.calc = function () {
            return this.calcPoints(), this.generatePaths()
        }, Line.prototype.calcPoints = function () {
            var row, y, _i, _len, _ref, _results;
            for (_ref = this.data, _results = [], _i = 0, _len = _ref.length; _len > _i; _i++) row = _ref[_i], row._x = this.transX(row.x), row._y = function () {
                var _j, _len1, _ref1, _results1;
                for (_ref1 = row.y, _results1 = [], _j = 0, _len1 = _ref1.length; _len1 > _j; _j++) y = _ref1[_j], null != y ? _results1.push(this.transY(y)) : _results1.push(y);
                return _results1
            }.call(this), _results.push(row._ymax = Math.min.apply(Math, [this.bottom].concat(function () {
                var _j, _len1, _ref1, _results1;
                for (_ref1 = row._y, _results1 = [], _j = 0, _len1 = _ref1.length; _len1 > _j; _j++) y = _ref1[_j], null != y && _results1.push(y);
                return _results1
            }())));
            return _results
        }, Line.prototype.hitTest = function (x) {
            var index, r, _i, _len, _ref;
            if (0 === this.data.length) return null;
            for (_ref = this.data.slice(1), index = _i = 0, _len = _ref.length; _len > _i && (r = _ref[index], !(x < (r._x + this.data[index]._x) / 2)); index = ++_i) ;
            return index
        }, Line.prototype.onGridClick = function (x, y) {
            var index;
            return index = this.hitTest(x), this.fire("click", index, this.data[index].src, x, y)
        }, Line.prototype.onHoverMove = function (x) {
            var index;
            return index = this.hitTest(x), this.displayHoverForRow(index)
        }, Line.prototype.onHoverOut = function () {
            return this.options.hideHover !== !1 ? this.displayHoverForRow(null) : void 0
        }, Line.prototype.displayHoverForRow = function (index) {
            var _ref;
            return null != index ? ((_ref = this.hover).update.apply(_ref, this.hoverContentForRow(index)), this.hilight(index)) : (this.hover.hide(), this.hilight())
        }, Line.prototype.hoverContentForRow = function (index) {
            var content, j, row, y, _i, _len, _ref;
            for (row = this.data[index], content = "<div class='morris-hover-row-label'>" + row.label + "</div>", _ref = row.y, j = _i = 0, _len = _ref.length; _len > _i; j = ++_i) y = _ref[j], content += "<div class='morris-hover-point' style='color: " + this.colorFor(row, j, "label") + "'>\n  " + this.options.labels[j] + ":\n  " + this.yLabelFormat(y) + "\n</div>";
            return "function" == typeof this.options.hoverCallback && (content = this.options.hoverCallback(index, this.options, content, row.src)), [content, row._x, row._ymax]
        }, Line.prototype.generatePaths = function () {
            var c, coords, i, r, smooth;
            return this.paths = function () {
                var _i, _ref, _ref1, _results;
                for (_results = [], i = _i = 0, _ref = this.options.ykeys.length; _ref >= 0 ? _ref > _i : _i > _ref; i = _ref >= 0 ? ++_i : --_i) smooth = "boolean" == typeof this.options.smooth ? this.options.smooth : (_ref1 = this.options.ykeys[i], __indexOf.call(this.options.smooth, _ref1) >= 0), coords = function () {
                    var _j, _len, _ref2, _results1;
                    for (_ref2 = this.data, _results1 = [], _j = 0, _len = _ref2.length; _len > _j; _j++) r = _ref2[_j], void 0 !== r._y[i] && _results1.push({
                        x: r._x,
                        y: r._y[i]
                    });
                    return _results1
                }.call(this), this.options.continuousLine && (coords = function () {
                    var _j, _len, _results1;
                    for (_results1 = [], _j = 0, _len = coords.length; _len > _j; _j++) c = coords[_j], null !== c.y && _results1.push(c);
                    return _results1
                }()), coords.length > 1 ? _results.push(Morris.Line.createPath(coords, smooth, this.bottom)) : _results.push(null);
                return _results
            }.call(this)
        }, Line.prototype.draw = function () {
            var _ref;
            return ((_ref = this.options.axes) === !0 || "both" === _ref || "x" === _ref) && this.drawXAxis(), this.drawSeries(), this.options.hideHover === !1 ? this.displayHoverForRow(this.data.length - 1) : void 0
        }, Line.prototype.drawXAxis = function () {
            var drawLabel, l, labels, prevAngleMargin, prevLabelMargin, row, ypos, _i, _len, _results, _this = this;
            for (ypos = this.bottom + this.options.padding / 2, prevLabelMargin = null, prevAngleMargin = null, drawLabel = function (labelText, xpos) {
                var label, labelBox, margin, offset, textBox;
                return label = _this.drawXAxisLabel(_this.transX(xpos), ypos, labelText), textBox = label.getBBox(), label.transform("r" + -_this.options.xLabelAngle), labelBox = label.getBBox(), label.transform("t0," + labelBox.height / 2 + "..."), 0 !== _this.options.xLabelAngle && (offset = -.5 * textBox.width * Math.cos(_this.options.xLabelAngle * Math.PI / 180), label.transform("t" + offset + ",0...")), labelBox = label.getBBox(), (null == prevLabelMargin || prevLabelMargin >= labelBox.x + labelBox.width || null != prevAngleMargin && prevAngleMargin >= labelBox.x) && labelBox.x >= 0 && labelBox.x + labelBox.width < _this.el.width() ? (0 !== _this.options.xLabelAngle && (margin = 1.25 * _this.options.gridTextSize / Math.sin(_this.options.xLabelAngle * Math.PI / 180), prevAngleMargin = labelBox.x - margin), prevLabelMargin = labelBox.x - _this.options.xLabelMargin) : label.remove()
            }, labels = this.options.parseTime ? 1 === this.data.length && "auto" === this.options.xLabels ? [[this.data[0].label, this.data[0].x]] : Morris.labelSeries(this.xmin, this.xmax, this.width, this.options.xLabels, this.options.xLabelFormat) : function () {
                var _i, _len, _ref, _results;
                for (_ref = this.data, _results = [], _i = 0, _len = _ref.length; _len > _i; _i++) row = _ref[_i], _results.push([row.label, row.x]);
                return _results
            }.call(this), labels.reverse(), _results = [], _i = 0, _len = labels.length; _len > _i; _i++) l = labels[_i], _results.push(drawLabel(l[0], l[1]));
            return _results
        }, Line.prototype.drawSeries = function () {
            var i, _i, _j, _ref, _ref1, _results;
            for (this.seriesPoints = [], i = _i = _ref = this.options.ykeys.length - 1; 0 >= _ref ? 0 >= _i : _i >= 0; i = 0 >= _ref ? ++_i : --_i) this._drawLineFor(i);
            for (_results = [], i = _j = _ref1 = this.options.ykeys.length - 1; 0 >= _ref1 ? 0 >= _j : _j >= 0; i = 0 >= _ref1 ? ++_j : --_j) _results.push(this._drawPointFor(i));
            return _results
        }, Line.prototype._drawPointFor = function (index) {
            var circle, row, _i, _len, _ref, _results;
            for (this.seriesPoints[index] = [], _ref = this.data, _results = [], _i = 0, _len = _ref.length; _len > _i; _i++) row = _ref[_i], circle = null, null != row._y[index] && (circle = this.drawLinePoint(row._x, row._y[index], this.colorFor(row, index, "point"), index)), _results.push(this.seriesPoints[index].push(circle));
            return _results
        }, Line.prototype._drawLineFor = function (index) {
            var path;
            return path = this.paths[index], null !== path ? this.drawLinePath(path, this.colorFor(null, index, "line"), index) : void 0
        }, Line.createPath = function (coords, smooth, bottom) {
            var coord, g, grads, i, ix, lg, path, prevCoord, x1, x2, y1, y2, _i, _len;
            for (path = "", smooth && (grads = Morris.Line.gradients(coords)), prevCoord = {y: null}, i = _i = 0, _len = coords.length; _len > _i; i = ++_i) coord = coords[i], null != coord.y && (null != prevCoord.y ? smooth ? (g = grads[i], lg = grads[i - 1], ix = (coord.x - prevCoord.x) / 4, x1 = prevCoord.x + ix, y1 = Math.min(bottom, prevCoord.y + ix * lg), x2 = coord.x - ix, y2 = Math.min(bottom, coord.y - ix * g), path += "C" + x1 + "," + y1 + "," + x2 + "," + y2 + "," + coord.x + "," + coord.y) : path += "L" + coord.x + "," + coord.y : smooth && null == grads[i] || (path += "M" + coord.x + "," + coord.y)), prevCoord = coord;
            return path
        }, Line.gradients = function (coords) {
            var coord, grad, i, nextCoord, prevCoord, _i, _len, _results;
            for (grad = function (a, b) {
                return (a.y - b.y) / (a.x - b.x)
            }, _results = [], i = _i = 0, _len = coords.length; _len > _i; i = ++_i) coord = coords[i], null != coord.y ? (nextCoord = coords[i + 1] || {y: null}, prevCoord = coords[i - 1] || {y: null}, null != prevCoord.y && null != nextCoord.y ? _results.push(grad(prevCoord, nextCoord)) : null != prevCoord.y ? _results.push(grad(prevCoord, coord)) : null != nextCoord.y ? _results.push(grad(coord, nextCoord)) : _results.push(null)) : _results.push(null);
            return _results
        }, Line.prototype.hilight = function (index) {
            var i, _i, _j, _ref, _ref1;
            if (null !== this.prevHilight && this.prevHilight !== index) for (i = _i = 0, _ref = this.seriesPoints.length - 1; _ref >= 0 ? _ref >= _i : _i >= _ref; i = _ref >= 0 ? ++_i : --_i) this.seriesPoints[i][this.prevHilight] && this.seriesPoints[i][this.prevHilight].animate(this.pointShrinkSeries(i));
            if (null !== index && this.prevHilight !== index) for (i = _j = 0, _ref1 = this.seriesPoints.length - 1; _ref1 >= 0 ? _ref1 >= _j : _j >= _ref1; i = _ref1 >= 0 ? ++_j : --_j) this.seriesPoints[i][index] && this.seriesPoints[i][index].animate(this.pointGrowSeries(i));
            return this.prevHilight = index
        }, Line.prototype.colorFor = function (row, sidx, type) {
            return "function" == typeof this.options.lineColors ? this.options.lineColors.call(this, row, sidx, type) : "point" === type ? this.options.pointFillColors[sidx % this.options.pointFillColors.length] || this.options.lineColors[sidx % this.options.lineColors.length] : this.options.lineColors[sidx % this.options.lineColors.length]
        }, Line.prototype.drawXAxisLabel = function (xPos, yPos, text) {
            return this.raphael.text(xPos, yPos, text).attr("font-size", this.options.gridTextSize).attr("font-family", this.options.gridTextFamily).attr("font-weight", this.options.gridTextWeight).attr("fill", this.options.gridTextColor)
        }, Line.prototype.drawLinePath = function (path, lineColor, lineIndex) {
            return this.raphael.path(path).attr("stroke", lineColor).attr("stroke-width", this.lineWidthForSeries(lineIndex))
        }, Line.prototype.drawLinePoint = function (xPos, yPos, pointColor, lineIndex) {
            return this.raphael.circle(xPos, yPos, this.pointSizeForSeries(lineIndex)).attr("fill", pointColor).attr("stroke-width", this.pointStrokeWidthForSeries(lineIndex)).attr("stroke", this.pointStrokeColorForSeries(lineIndex))
        }, Line.prototype.pointStrokeWidthForSeries = function (index) {
            return this.options.pointStrokeWidths[index % this.options.pointStrokeWidths.length]
        }, Line.prototype.pointStrokeColorForSeries = function (index) {
            return this.options.pointStrokeColors[index % this.options.pointStrokeColors.length]
        }, Line.prototype.lineWidthForSeries = function (index) {
            return this.options.lineWidth instanceof Array ? this.options.lineWidth[index % this.options.lineWidth.length] : this.options.lineWidth
        }, Line.prototype.pointSizeForSeries = function (index) {
            return this.options.pointSize instanceof Array ? this.options.pointSize[index % this.options.pointSize.length] : this.options.pointSize
        }, Line.prototype.pointGrowSeries = function (index) {
            return Raphael.animation({r: this.pointSizeForSeries(index) + 3}, 25, "linear")
        }, Line.prototype.pointShrinkSeries = function (index) {
            return Raphael.animation({r: this.pointSizeForSeries(index)}, 25, "linear")
        }, Line
    }(Morris.Grid), Morris.labelSeries = function (dmin, dmax, pxwidth, specName, xLabelFormat) {
        var d, d0, ddensity, name, ret, s, spec, t, _i, _len, _ref;
        if (ddensity = 200 * (dmax - dmin) / pxwidth, d0 = new Date(dmin), spec = Morris.LABEL_SPECS[specName], void 0 === spec) for (_ref = Morris.AUTO_LABEL_ORDER, _i = 0, _len = _ref.length; _len > _i; _i++) if (name = _ref[_i], s = Morris.LABEL_SPECS[name], ddensity >= s.span) {
            spec = s;
            break
        }
        for (void 0 === spec && (spec = Morris.LABEL_SPECS.second), xLabelFormat && (spec = $.extend({}, spec, {fmt: xLabelFormat})), d = spec.start(d0), ret = []; (t = d.getTime()) <= dmax;) t >= dmin && ret.push([spec.fmt(d), t]), spec.incr(d);
        return ret
    }, minutesSpecHelper = function (interval) {
        return {
            span: 60 * interval * 1e3, start: function (d) {
                return new Date(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours())
            }, fmt: function (d) {
                return "" + Morris.pad2(d.getHours()) + ":" + Morris.pad2(d.getMinutes())
            }, incr: function (d) {
                return d.setUTCMinutes(d.getUTCMinutes() + interval)
            }
        }
    }, secondsSpecHelper = function (interval) {
        return {
            span: 1e3 * interval, start: function (d) {
                return new Date(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes())
            }, fmt: function (d) {
                return "" + Morris.pad2(d.getHours()) + ":" + Morris.pad2(d.getMinutes()) + ":" + Morris.pad2(d.getSeconds())
            }, incr: function (d) {
                return d.setUTCSeconds(d.getUTCSeconds() + interval)
            }
        }
    }, Morris.LABEL_SPECS = {
        decade: {
            span: 1728e8, start: function (d) {
                return new Date(d.getFullYear() - d.getFullYear() % 10, 0, 1)
            }, fmt: function (d) {
                return "" + d.getFullYear()
            }, incr: function (d) {
                return d.setFullYear(d.getFullYear() + 10)
            }
        },
        year: {
            span: 1728e7, start: function (d) {
                return new Date(d.getFullYear(), 0, 1)
            }, fmt: function (d) {
                return "" + d.getFullYear()
            }, incr: function (d) {
                return d.setFullYear(d.getFullYear() + 1)
            }
        },
        month: {
            span: 24192e5, start: function (d) {
                return new Date(d.getFullYear(), d.getMonth(), 1)
            }, fmt: function (d) {
                return "" + d.getFullYear() + "-" + Morris.pad2(d.getMonth() + 1)
            }, incr: function (d) {
                return d.setMonth(d.getMonth() + 1)
            }
        },
        week: {
            span: 6048e5, start: function (d) {
                return new Date(d.getFullYear(), d.getMonth(), d.getDate())
            }, fmt: function (d) {
                return "" + d.getFullYear() + "-" + Morris.pad2(d.getMonth() + 1) + "-" + Morris.pad2(d.getDate())
            }, incr: function (d) {
                return d.setDate(d.getDate() + 7)
            }
        },
        day: {
            span: 864e5, start: function (d) {
                return new Date(d.getFullYear(), d.getMonth(), d.getDate())
            }, fmt: function (d) {
                return "" + d.getFullYear() + "-" + Morris.pad2(d.getMonth() + 1) + "-" + Morris.pad2(d.getDate())
            }, incr: function (d) {
                return d.setDate(d.getDate() + 1)
            }
        },
        hour: minutesSpecHelper(60),
        "30min": minutesSpecHelper(30),
        "15min": minutesSpecHelper(15),
        "10min": minutesSpecHelper(10),
        "5min": minutesSpecHelper(5),
        minute: minutesSpecHelper(1),
        "30sec": secondsSpecHelper(30),
        "15sec": secondsSpecHelper(15),
        "10sec": secondsSpecHelper(10),
        "5sec": secondsSpecHelper(5),
        second: secondsSpecHelper(1)
    }, Morris.AUTO_LABEL_ORDER = ["decade", "year", "month", "week", "day", "hour", "30min", "15min", "10min", "5min", "minute", "30sec", "15sec", "10sec", "5sec", "second"], Morris.Area = function (_super) {
        function Area(options) {
            var areaOptions;
            return this instanceof Morris.Area ? (areaOptions = $.extend({}, areaDefaults, options), this.cumulative = !areaOptions.behaveLikeLine, "auto" === areaOptions.fillOpacity && (areaOptions.fillOpacity = areaOptions.behaveLikeLine ? .8 : 1), Area.__super__.constructor.call(this, areaOptions), void 0) : new Morris.Area(options)
        }

        var areaDefaults;
        return __extends(Area, _super), areaDefaults = {
            fillOpacity: "auto",
            behaveLikeLine: !1
        }, Area.prototype.calcPoints = function () {
            var row, total, y, _i, _len, _ref, _results;
            for (_ref = this.data, _results = [], _i = 0, _len = _ref.length; _len > _i; _i++) row = _ref[_i], row._x = this.transX(row.x), total = 0, row._y = function () {
                var _j, _len1, _ref1, _results1;
                for (_ref1 = row.y, _results1 = [], _j = 0, _len1 = _ref1.length; _len1 > _j; _j++) y = _ref1[_j], this.options.behaveLikeLine ? _results1.push(this.transY(y)) : (total += y || 0, _results1.push(this.transY(total)));
                return _results1
            }.call(this), _results.push(row._ymax = Math.max.apply(Math, row._y));
            return _results
        }, Area.prototype.drawSeries = function () {
            var i, range, _k, _len, _ref1, _results, _results1, _results2;
            for (this.seriesPoints = [], range = this.options.behaveLikeLine ? function () {
                _results = [];
                for (var _i = 0, _ref = this.options.ykeys.length - 1; _ref >= 0 ? _ref >= _i : _i >= _ref; _ref >= 0 ? _i++ : _i--) _results.push(_i);
                return _results
            }.apply(this) : function () {
                _results1 = [];
                for (var _j = _ref1 = this.options.ykeys.length - 1; 0 >= _ref1 ? 0 >= _j : _j >= 0; 0 >= _ref1 ? _j++ : _j--) _results1.push(_j);
                return _results1
            }.apply(this), _results2 = [], _k = 0, _len = range.length; _len > _k; _k++) i = range[_k], this._drawFillFor(i), this._drawLineFor(i), _results2.push(this._drawPointFor(i));
            return _results2
        }, Area.prototype._drawFillFor = function (index) {
            var path;
            return path = this.paths[index], null !== path ? (path += "L" + this.transX(this.xmax) + "," + this.bottom + "L" + this.transX(this.xmin) + "," + this.bottom + "Z", this.drawFilledPath(path, this.fillForSeries(index))) : void 0
        }, Area.prototype.fillForSeries = function (i) {
            var color;
            return color = Raphael.rgb2hsl(this.colorFor(this.data[i], i, "line")), Raphael.hsl(color.h, this.options.behaveLikeLine ? .9 * color.s : .75 * color.s, Math.min(.98, this.options.behaveLikeLine ? 1.2 * color.l : 1.25 * color.l))
        }, Area.prototype.drawFilledPath = function (path, fill) {
            return this.raphael.path(path).attr("fill", fill).attr("fill-opacity", this.options.fillOpacity).attr("stroke", "none")
        }, Area
    }(Morris.Line), Morris.Bar = function (_super) {
        function Bar(options) {
            return this.onHoverOut = __bind(this.onHoverOut, this), this.onHoverMove = __bind(this.onHoverMove, this), this.onGridClick = __bind(this.onGridClick, this), this instanceof Morris.Bar ? (Bar.__super__.constructor.call(this, $.extend({}, options, {parseTime: !1})), void 0) : new Morris.Bar(options)
        }

        return __extends(Bar, _super), Bar.prototype.init = function () {
            return this.cumulative = this.options.stacked, "always" !== this.options.hideHover ? (this.hover = new Morris.Hover({parent: this.el}), this.on("hovermove", this.onHoverMove), this.on("hoverout", this.onHoverOut), this.on("gridclick", this.onGridClick)) : void 0
        }, Bar.prototype.defaults = {
            barSizeRatio: .75,
            barGap: 3,
            barColors: ["#0b62a4", "#7a92a3", "#4da74d", "#afd8f8", "#edc240", "#cb4b4b", "#9440ed"],
            barOpacity: 1,
            barRadius: [0, 0, 0, 0],
            xLabelMargin: 50
        }, Bar.prototype.calc = function () {
            var _ref;
            return this.calcBars(), this.options.hideHover === !1 ? (_ref = this.hover).update.apply(_ref, this.hoverContentForRow(this.data.length - 1)) : void 0
        }, Bar.prototype.calcBars = function () {
            var idx, row, y, _i, _len, _ref, _results;
            for (_ref = this.data, _results = [], idx = _i = 0, _len = _ref.length; _len > _i; idx = ++_i) row = _ref[idx], row._x = this.left + this.width * (idx + .5) / this.data.length, _results.push(row._y = function () {
                var _j, _len1, _ref1, _results1;
                for (_ref1 = row.y, _results1 = [], _j = 0, _len1 = _ref1.length; _len1 > _j; _j++) y = _ref1[_j], null != y ? _results1.push(this.transY(y)) : _results1.push(null);
                return _results1
            }.call(this));
            return _results
        }, Bar.prototype.draw = function () {
            var _ref;
            return ((_ref = this.options.axes) === !0 || "both" === _ref || "x" === _ref) && this.drawXAxis(), this.drawSeries()
        }, Bar.prototype.drawXAxis = function () {
            var i, label, labelBox, margin, offset, prevAngleMargin, prevLabelMargin, row, textBox, ypos, _i, _ref,
                _results;
            for (ypos = this.bottom + (this.options.xAxisLabelTopPadding || this.options.padding / 2), prevLabelMargin = null, prevAngleMargin = null, _results = [], i = _i = 0, _ref = this.data.length; _ref >= 0 ? _ref > _i : _i > _ref; i = _ref >= 0 ? ++_i : --_i) row = this.data[this.data.length - 1 - i], label = this.drawXAxisLabel(row._x, ypos, row.label), textBox = label.getBBox(), label.transform("r" + -this.options.xLabelAngle), labelBox = label.getBBox(), label.transform("t0," + labelBox.height / 2 + "..."), 0 !== this.options.xLabelAngle && (offset = -.5 * textBox.width * Math.cos(this.options.xLabelAngle * Math.PI / 180), label.transform("t" + offset + ",0...")), (null == prevLabelMargin || prevLabelMargin >= labelBox.x + labelBox.width || null != prevAngleMargin && prevAngleMargin >= labelBox.x) && labelBox.x >= 0 && labelBox.x + labelBox.width < this.el.width() ? (0 !== this.options.xLabelAngle && (margin = 1.25 * this.options.gridTextSize / Math.sin(this.options.xLabelAngle * Math.PI / 180), prevAngleMargin = labelBox.x - margin), _results.push(prevLabelMargin = labelBox.x - this.options.xLabelMargin)) : _results.push(label.remove());
            return _results
        }, Bar.prototype.drawSeries = function () {
            var barWidth, bottom, groupWidth, idx, lastTop, left, leftPadding, numBars, row, sidx, size, top, ypos,
                zeroPos;
            return groupWidth = this.width / this.options.data.length, numBars = null != this.options.stacked ? 1 : this.options.ykeys.length, barWidth = (groupWidth * this.options.barSizeRatio - this.options.barGap * (numBars - 1)) / numBars, leftPadding = groupWidth * (1 - this.options.barSizeRatio) / 2, zeroPos = this.ymin <= 0 && this.ymax >= 0 ? this.transY(0) : null, this.bars = function () {
                var _i, _len, _ref, _results;
                for (_ref = this.data, _results = [], idx = _i = 0, _len = _ref.length; _len > _i; idx = ++_i) row = _ref[idx], lastTop = 0, _results.push(function () {
                    var _j, _len1, _ref1, _results1;
                    for (_ref1 = row._y, _results1 = [], sidx = _j = 0, _len1 = _ref1.length; _len1 > _j; sidx = ++_j) ypos = _ref1[sidx], null !== ypos ? (zeroPos ? (top = Math.min(ypos, zeroPos), bottom = Math.max(ypos, zeroPos)) : (top = ypos, bottom = this.bottom), left = this.left + idx * groupWidth + leftPadding, this.options.stacked || (left += sidx * (barWidth + this.options.barGap)), size = bottom - top, this.options.stacked && (top -= lastTop), this.drawBar(left, top, barWidth, size, this.colorFor(row, sidx, "bar"), this.options.barOpacity, this.options.barRadius), _results1.push(lastTop += size)) : _results1.push(null);
                    return _results1
                }.call(this));
                return _results
            }.call(this)
        }, Bar.prototype.colorFor = function (row, sidx, type) {
            var r, s;
            return "function" == typeof this.options.barColors ? (r = {
                x: row.x,
                y: row.y[sidx],
                label: row.label
            }, s = {
                index: sidx,
                key: this.options.ykeys[sidx],
                label: this.options.labels[sidx]
            }, this.options.barColors.call(this, r, s, type)) : this.options.barColors[sidx % this.options.barColors.length]
        }, Bar.prototype.hitTest = function (x) {
            return 0 === this.data.length ? null : (x = Math.max(Math.min(x, this.right), this.left), Math.min(this.data.length - 1, Math.floor((x - this.left) / (this.width / this.data.length))))
        }, Bar.prototype.onGridClick = function (x, y) {
            var index;
            return index = this.hitTest(x), this.fire("click", index, this.data[index].src, x, y)
        }, Bar.prototype.onHoverMove = function (x) {
            var index, _ref;
            return index = this.hitTest(x), (_ref = this.hover).update.apply(_ref, this.hoverContentForRow(index))
        }, Bar.prototype.onHoverOut = function () {
            return this.options.hideHover !== !1 ? this.hover.hide() : void 0
        }, Bar.prototype.hoverContentForRow = function (index) {
            var content, j, row, x, y, _i, _len, _ref;
            for (row = this.data[index], content = "<div class='morris-hover-row-label'>" + row.label + "</div>", _ref = row.y, j = _i = 0, _len = _ref.length; _len > _i; j = ++_i) y = _ref[j], content += "<div class='morris-hover-point' style='color: " + this.colorFor(row, j, "label") + "'>\n  " + this.options.labels[j] + ":\n  " + this.yLabelFormat(y) + "\n</div>";
            return "function" == typeof this.options.hoverCallback && (content = this.options.hoverCallback(index, this.options, content, row.src)), x = this.left + (index + .5) * this.width / this.data.length, [content, x]
        }, Bar.prototype.drawXAxisLabel = function (xPos, yPos, text) {
            var label;
            return label = this.raphael.text(xPos, yPos, text).attr("font-size", this.options.gridTextSize).attr("font-family", this.options.gridTextFamily).attr("font-weight", this.options.gridTextWeight).attr("fill", this.options.gridTextColor)
        }, Bar.prototype.drawBar = function (xPos, yPos, width, height, barColor, opacity, radiusArray) {
            var maxRadius, path;
            return maxRadius = Math.max.apply(Math, radiusArray), path = 0 === maxRadius || maxRadius > height ? this.raphael.rect(xPos, yPos, width, height) : this.raphael.path(this.roundedRect(xPos, yPos, width, height, radiusArray)), path.attr("fill", barColor).attr("fill-opacity", opacity).attr("stroke", "none")
        }, Bar.prototype.roundedRect = function (x, y, w, h, r) {
            return null == r && (r = [0, 0, 0, 0]), ["M", x, r[0] + y, "Q", x, y, x + r[0], y, "L", x + w - r[1], y, "Q", x + w, y, x + w, y + r[1], "L", x + w, y + h - r[2], "Q", x + w, y + h, x + w - r[2], y + h, "L", x + r[3], y + h, "Q", x, y + h, x, y + h - r[3], "Z"]
        }, Bar
    }(Morris.Grid), Morris.Donut = function (_super) {
        function Donut(options) {
            this.resizeHandler = __bind(this.resizeHandler, this), this.select = __bind(this.select, this), this.click = __bind(this.click, this);
            var _this = this;
            if (!(this instanceof Morris.Donut)) return new Morris.Donut(options);
            if (this.options = $.extend({}, this.defaults, options), this.el = "string" == typeof options.element ? $(document.getElementById(options.element)) : $(options.element), null === this.el || 0 === this.el.length) throw new Error("Graph placeholder not found.");
            void 0 !== options.data && 0 !== options.data.length && (this.raphael = new Raphael(this.el[0]), this.options.resize && $(window).bind("resize", function () {
                return null != _this.timeoutId && window.clearTimeout(_this.timeoutId), _this.timeoutId = window.setTimeout(_this.resizeHandler, 100)
            }), this.setData(options.data))
        }

        return __extends(Donut, _super), Donut.prototype.defaults = {
            colors: ["#0B62A4", "#3980B5", "#679DC6", "#95BBD7", "#B0CCE1", "#095791", "#095085", "#083E67", "#052C48", "#042135"],
            backgroundColor: "#FFFFFF",
            labelColor: "#000000",
            formatter: Morris.commas,
            resize: !1
        }, Donut.prototype.redraw = function () {
            var C, cx, cy, i, idx, last, max_value, min, next, seg, total, value, w, _i, _j, _k, _len, _len1, _len2,
                _ref, _ref1, _ref2, _results;
            for (this.raphael.clear(), cx = this.el.width() / 2, cy = this.el.height() / 2, w = (Math.min(cx, cy) - 10) / 3, total = 0, _ref = this.values, _i = 0, _len = _ref.length; _len > _i; _i++) value = _ref[_i], total += value;
            for (min = 5 / (2 * w), C = 1.9999 * Math.PI - min * this.data.length, last = 0, idx = 0, this.segments = [], _ref1 = this.values, i = _j = 0, _len1 = _ref1.length; _len1 > _j; i = ++_j) value = _ref1[i], next = last + min + C * (value / total), seg = new Morris.DonutSegment(cx, cy, 2 * w, w, last, next, this.data[i].color || this.options.colors[idx % this.options.colors.length], this.options.backgroundColor, idx, this.raphael), seg.render(), this.segments.push(seg), seg.on("hover", this.select), seg.on("click", this.click), last = next, idx += 1;
            for (this.text1 = this.drawEmptyDonutLabel(cx, cy - 10, this.options.labelColor, 15, 800), this.text2 = this.drawEmptyDonutLabel(cx, cy + 10, this.options.labelColor, 14), max_value = Math.max.apply(Math, this.values), idx = 0, _ref2 = this.values, _results = [], _k = 0, _len2 = _ref2.length; _len2 > _k; _k++) {
                if (value = _ref2[_k], value === max_value) {
                    this.select(idx);
                    break
                }
                _results.push(idx += 1)
            }
            return _results
        }, Donut.prototype.setData = function (data) {
            var row;
            return this.data = data, this.values = function () {
                var _i, _len, _ref, _results;
                for (_ref = this.data, _results = [], _i = 0, _len = _ref.length; _len > _i; _i++) row = _ref[_i], _results.push(parseFloat(row.value));
                return _results
            }.call(this), this.redraw()
        }, Donut.prototype.click = function (idx) {
            return this.fire("click", idx, this.data[idx])
        }, Donut.prototype.select = function (idx) {
            var row, s, segment, _i, _len, _ref;
            for (_ref = this.segments, _i = 0, _len = _ref.length; _len > _i; _i++) s = _ref[_i], s.deselect();
            return segment = this.segments[idx], segment.select(), row = this.data[idx], this.setLabels(row.label, this.options.formatter(row.value, row))
        }, Donut.prototype.setLabels = function (label1, label2) {
            var inner, maxHeightBottom, maxHeightTop, maxWidth, text1bbox, text1scale, text2bbox, text2scale;
            return inner = 2 * (Math.min(this.el.width() / 2, this.el.height() / 2) - 10) / 3, maxWidth = 1.8 * inner, maxHeightTop = inner / 2, maxHeightBottom = inner / 3, this.text1.attr({
                text: label1,
                transform: ""
            }), text1bbox = this.text1.getBBox(), text1scale = Math.min(maxWidth / text1bbox.width, maxHeightTop / text1bbox.height), this.text1.attr({transform: "S" + text1scale + "," + text1scale + "," + (text1bbox.x + text1bbox.width / 2) + "," + (text1bbox.y + text1bbox.height)}), this.text2.attr({
                text: label2,
                transform: ""
            }), text2bbox = this.text2.getBBox(), text2scale = Math.min(maxWidth / text2bbox.width, maxHeightBottom / text2bbox.height), this.text2.attr({transform: "S" + text2scale + "," + text2scale + "," + (text2bbox.x + text2bbox.width / 2) + "," + text2bbox.y})
        }, Donut.prototype.drawEmptyDonutLabel = function (xPos, yPos, color, fontSize, fontWeight) {
            var text;
            return text = this.raphael.text(xPos, yPos, "").attr("font-size", fontSize).attr("fill", color), null != fontWeight && text.attr("font-weight", fontWeight), text
        }, Donut.prototype.resizeHandler = function () {
            return this.timeoutId = null, this.raphael.setSize(this.el.width(), this.el.height()), this.redraw()
        }, Donut
    }(Morris.EventEmitter), Morris.DonutSegment = function (_super) {
        function DonutSegment(cx, cy, inner, outer, p0, p1, color, backgroundColor, index, raphael) {
            this.cx = cx, this.cy = cy, this.inner = inner, this.outer = outer, this.color = color, this.backgroundColor = backgroundColor, this.index = index, this.raphael = raphael, this.deselect = __bind(this.deselect, this), this.select = __bind(this.select, this), this.sin_p0 = Math.sin(p0), this.cos_p0 = Math.cos(p0), this.sin_p1 = Math.sin(p1), this.cos_p1 = Math.cos(p1), this.is_long = p1 - p0 > Math.PI ? 1 : 0, this.path = this.calcSegment(this.inner + 3, this.inner + this.outer - 5), this.selectedPath = this.calcSegment(this.inner + 3, this.inner + this.outer), this.hilight = this.calcArc(this.inner)
        }

        return __extends(DonutSegment, _super), DonutSegment.prototype.calcArcPoints = function (r) {
            return [this.cx + r * this.sin_p0, this.cy + r * this.cos_p0, this.cx + r * this.sin_p1, this.cy + r * this.cos_p1]
        }, DonutSegment.prototype.calcSegment = function (r1, r2) {
            var ix0, ix1, iy0, iy1, ox0, ox1, oy0, oy1, _ref, _ref1;
            return _ref = this.calcArcPoints(r1), ix0 = _ref[0], iy0 = _ref[1], ix1 = _ref[2], iy1 = _ref[3], _ref1 = this.calcArcPoints(r2), ox0 = _ref1[0], oy0 = _ref1[1], ox1 = _ref1[2], oy1 = _ref1[3], "M" + ix0 + "," + iy0 + ("A" + r1 + "," + r1 + ",0," + this.is_long + ",0," + ix1 + "," + iy1) + ("L" + ox1 + "," + oy1) + ("A" + r2 + "," + r2 + ",0," + this.is_long + ",1," + ox0 + "," + oy0) + "Z"
        }, DonutSegment.prototype.calcArc = function (r) {
            var ix0, ix1, iy0, iy1, _ref;
            return _ref = this.calcArcPoints(r), ix0 = _ref[0], iy0 = _ref[1], ix1 = _ref[2], iy1 = _ref[3], "M" + ix0 + "," + iy0 + ("A" + r + "," + r + ",0," + this.is_long + ",0," + ix1 + "," + iy1)
        }, DonutSegment.prototype.render = function () {
            var _this = this;
            return this.arc = this.drawDonutArc(this.hilight, this.color), this.seg = this.drawDonutSegment(this.path, this.color, this.backgroundColor, function () {
                return _this.fire("hover", _this.index)
            }, function () {
                return _this.fire("click", _this.index)
            })
        }, DonutSegment.prototype.drawDonutArc = function (path, color) {
            return this.raphael.path(path).attr({stroke: color, "stroke-width": 2, opacity: 0})
        }, DonutSegment.prototype.drawDonutSegment = function (path, fillColor, strokeColor, hoverFunction, clickFunction) {
            return this.raphael.path(path).attr({
                fill: fillColor,
                stroke: strokeColor,
                "stroke-width": 3
            }).hover(hoverFunction).click(clickFunction)
        }, DonutSegment.prototype.select = function () {
            return this.selected ? void 0 : (this.seg.animate({path: this.selectedPath}, 150, "<>"), this.arc.animate({opacity: 1}, 150, "<>"), this.selected = !0)
        }, DonutSegment.prototype.deselect = function () {
            return this.selected ? (this.seg.animate({path: this.path}, 150, "<>"), this.arc.animate({opacity: 0}, 150, "<>"), this.selected = !1) : void 0
        }, DonutSegment
    }(Morris.EventEmitter)
}).call(this);