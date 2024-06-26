!function (glob) {
    var current_event, stop, version = "0.4.2", has = "hasOwnProperty", separator = /[\.\/]/, wildcard = "*",
        fun = function () {
        }, numsort = function (a, b) {
            return a - b
        }, events = {n: {}}, eve = function (name, scope) {
            name = String(name);
            var l, oldstop = stop, args = Array.prototype.slice.call(arguments, 2), listeners = eve.listeners(name), z = 0,
                indexed = [], queue = {}, out = [], ce = current_event;
            current_event = name, stop = 0;
            for (var i = 0, ii = listeners.length; ii > i; i++) "zIndex" in listeners[i] && (indexed.push(listeners[i].zIndex), listeners[i].zIndex < 0 && (queue[listeners[i].zIndex] = listeners[i]));
            for (indexed.sort(numsort); indexed[z] < 0;) if (l = queue[indexed[z++]], out.push(l.apply(scope, args)), stop) return stop = oldstop, out;
            for (i = 0; ii > i; i++) if (l = listeners[i], "zIndex" in l) if (l.zIndex == indexed[z]) {
                if (out.push(l.apply(scope, args)), stop) break;
                do if (z++, l = queue[indexed[z]], l && out.push(l.apply(scope, args)), stop) break; while (l)
            } else queue[l.zIndex] = l; else if (out.push(l.apply(scope, args)), stop) break;
            return stop = oldstop, current_event = ce, out.length ? out : null
        };
    eve._events = events, eve.listeners = function (name) {
        var item, items, k, i, ii, j, jj, nes, names = name.split(separator), e = events, es = [e], out = [];
        for (i = 0, ii = names.length; ii > i; i++) {
            for (nes = [], j = 0, jj = es.length; jj > j; j++) for (e = es[j].n, items = [e[names[i]], e[wildcard]], k = 2; k--;) item = items[k], item && (nes.push(item), out = out.concat(item.f || []));
            es = nes
        }
        return out
    }, eve.on = function (name, f) {
        if (name = String(name), "function" != typeof f) return function () {
        };
        for (var names = name.split(separator), e = events, i = 0, ii = names.length; ii > i; i++) e = e.n, e = e.hasOwnProperty(names[i]) && e[names[i]] || (e[names[i]] = {n: {}});
        for (e.f = e.f || [], i = 0, ii = e.f.length; ii > i; i++) if (e.f[i] == f) return fun;
        return e.f.push(f), function (zIndex) {
            +zIndex == +zIndex && (f.zIndex = +zIndex)
        }
    }, eve.f = function (event) {
        var attrs = [].slice.call(arguments, 1);
        return function () {
            eve.apply(null, [event, null].concat(attrs).concat([].slice.call(arguments, 0)))
        }
    }, eve.stop = function () {
        stop = 1
    }, eve.nt = function (subname) {
        return subname ? new RegExp("(?:\\.|\\/|^)" + subname + "(?:\\.|\\/|$)").test(current_event) : current_event
    }, eve.nts = function () {
        return current_event.split(separator)
    }, eve.off = eve.unbind = function (name, f) {
        if (!name) return eve._events = events = {n: {}}, void 0;
        var e, key, splice, i, ii, j, jj, names = name.split(separator), cur = [events];
        for (i = 0, ii = names.length; ii > i; i++) for (j = 0; j < cur.length; j += splice.length - 2) {
            if (splice = [j, 1], e = cur[j].n, names[i] != wildcard) e[names[i]] && splice.push(e[names[i]]); else for (key in e) e[has](key) && splice.push(e[key]);
            cur.splice.apply(cur, splice)
        }
        for (i = 0, ii = cur.length; ii > i; i++) for (e = cur[i]; e.n;) {
            if (f) {
                if (e.f) {
                    for (j = 0, jj = e.f.length; jj > j; j++) if (e.f[j] == f) {
                        e.f.splice(j, 1);
                        break
                    }
                    !e.f.length && delete e.f
                }
                for (key in e.n) if (e.n[has](key) && e.n[key].f) {
                    var funcs = e.n[key].f;
                    for (j = 0, jj = funcs.length; jj > j; j++) if (funcs[j] == f) {
                        funcs.splice(j, 1);
                        break
                    }
                    !funcs.length && delete e.n[key].f
                }
            } else {
                delete e.f;
                for (key in e.n) e.n[has](key) && e.n[key].f && delete e.n[key].f
            }
            e = e.n
        }
    }, eve.once = function (name, f) {
        var f2 = function () {
            return eve.unbind(name, f2), f.apply(this, arguments)
        };
        return eve.on(name, f2)
    }, eve.version = version, eve.toString = function () {
        return "You are running Eve " + version
    }, "undefined" != typeof module && module.exports ? module.exports = eve : "undefined" != typeof define ? define("eve", [], function () {
        return eve
    }) : glob.eve = eve
}(this), function (glob, factory) {
    "function" == typeof define && define.amd ? define(["eve"], function (eve) {
        return factory(glob, eve)
    }) : factory(glob, glob.eve)
}(this, function (window, eve) {
    function R(first) {
        if (R.is(first, "function")) return loaded ? first() : eve.on("raphael.DOMload", first);
        if (R.is(first, array)) return R._engine.create[apply](R, first.splice(0, 3 + R.is(first[0], nu))).add(first);
        var args = Array.prototype.slice.call(arguments, 0);
        if (R.is(args[args.length - 1], "function")) {
            var f = args.pop();
            return loaded ? f.call(R._engine.create[apply](R, args)) : eve.on("raphael.DOMload", function () {
                f.call(R._engine.create[apply](R, args))
            })
        }
        return R._engine.create[apply](R, arguments)
    }

    function clone(obj) {
        if ("function" == typeof obj || Object(obj) !== obj) return obj;
        var res = new obj.constructor;
        for (var key in obj) obj[has](key) && (res[key] = clone(obj[key]));
        return res
    }

    function repush(array, item) {
        for (var i = 0, ii = array.length; ii > i; i++) if (array[i] === item) return array.push(array.splice(i, 1)[0])
    }

    function cacher(f, scope, postprocessor) {
        function newf() {
            var arg = Array.prototype.slice.call(arguments, 0), args = arg.join("鈵€"),
                cache = newf.cache = newf.cache || {}, count = newf.count = newf.count || [];
            return cache[has](args) ? (repush(count, args), postprocessor ? postprocessor(cache[args]) : cache[args]) : (count.length >= 1e3 && delete cache[count.shift()], count.push(args), cache[args] = f[apply](scope, arg), postprocessor ? postprocessor(cache[args]) : cache[args])
        }

        return newf
    }

    function clrToString() {
        return this.hex
    }

    function catmullRom2bezier(crp, z) {
        for (var d = [], i = 0, iLen = crp.length; iLen - 2 * !z > i; i += 2) {
            var p = [{x: +crp[i - 2], y: +crp[i - 1]}, {x: +crp[i], y: +crp[i + 1]}, {
                x: +crp[i + 2],
                y: +crp[i + 3]
            }, {x: +crp[i + 4], y: +crp[i + 5]}];
            z ? i ? iLen - 4 == i ? p[3] = {x: +crp[0], y: +crp[1]} : iLen - 2 == i && (p[2] = {
                x: +crp[0],
                y: +crp[1]
            }, p[3] = {x: +crp[2], y: +crp[3]}) : p[0] = {
                x: +crp[iLen - 2],
                y: +crp[iLen - 1]
            } : iLen - 4 == i ? p[3] = p[2] : i || (p[0] = {
                x: +crp[i],
                y: +crp[i + 1]
            }), d.push(["C", (-p[0].x + 6 * p[1].x + p[2].x) / 6, (-p[0].y + 6 * p[1].y + p[2].y) / 6, (p[1].x + 6 * p[2].x - p[3].x) / 6, (p[1].y + 6 * p[2].y - p[3].y) / 6, p[2].x, p[2].y])
        }
        return d
    }

    function base3(t, p1, p2, p3, p4) {
        var t1 = -3 * p1 + 9 * p2 - 9 * p3 + 3 * p4, t2 = t * t1 + 6 * p1 - 12 * p2 + 6 * p3;
        return t * t2 - 3 * p1 + 3 * p2
    }

    function bezlen(x1, y1, x2, y2, x3, y3, x4, y4, z) {
        null == z && (z = 1), z = z > 1 ? 1 : 0 > z ? 0 : z;
        for (var z2 = z / 2, n = 12, Tvalues = [-.1252, .1252, -.3678, .3678, -.5873, .5873, -.7699, .7699, -.9041, .9041, -.9816, .9816], Cvalues = [.2491, .2491, .2335, .2335, .2032, .2032, .1601, .1601, .1069, .1069, .0472, .0472], sum = 0, i = 0; n > i; i++) {
            var ct = z2 * Tvalues[i] + z2, xbase = base3(ct, x1, x2, x3, x4), ybase = base3(ct, y1, y2, y3, y4),
                comb = xbase * xbase + ybase * ybase;
            sum += Cvalues[i] * math.sqrt(comb)
        }
        return z2 * sum
    }

    function getTatLen(x1, y1, x2, y2, x3, y3, x4, y4, ll) {
        if (!(0 > ll || bezlen(x1, y1, x2, y2, x3, y3, x4, y4) < ll)) {
            var l, t = 1, step = t / 2, t2 = t - step, e = .01;
            for (l = bezlen(x1, y1, x2, y2, x3, y3, x4, y4, t2); abs(l - ll) > e;) step /= 2, t2 += (ll > l ? 1 : -1) * step, l = bezlen(x1, y1, x2, y2, x3, y3, x4, y4, t2);
            return t2
        }
    }

    function intersect(x1, y1, x2, y2, x3, y3, x4, y4) {
        if (!(mmax(x1, x2) < mmin(x3, x4) || mmin(x1, x2) > mmax(x3, x4) || mmax(y1, y2) < mmin(y3, y4) || mmin(y1, y2) > mmax(y3, y4))) {
            var nx = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4),
                ny = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4),
                denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
            if (denominator) {
                var px = nx / denominator, py = ny / denominator, px2 = +px.toFixed(2), py2 = +py.toFixed(2);
                if (!(px2 < +mmin(x1, x2).toFixed(2) || px2 > +mmax(x1, x2).toFixed(2) || px2 < +mmin(x3, x4).toFixed(2) || px2 > +mmax(x3, x4).toFixed(2) || py2 < +mmin(y1, y2).toFixed(2) || py2 > +mmax(y1, y2).toFixed(2) || py2 < +mmin(y3, y4).toFixed(2) || py2 > +mmax(y3, y4).toFixed(2))) return {
                    x: px,
                    y: py
                }
            }
        }
    }

    function interHelper(bez1, bez2, justCount) {
        var bbox1 = R.bezierBBox(bez1), bbox2 = R.bezierBBox(bez2);
        if (!R.isBBoxIntersect(bbox1, bbox2)) return justCount ? 0 : [];
        for (var l1 = bezlen.apply(0, bez1), l2 = bezlen.apply(0, bez2), n1 = mmax(~~(l1 / 5), 1), n2 = mmax(~~(l2 / 5), 1), dots1 = [], dots2 = [], xy = {}, res = justCount ? 0 : [], i = 0; n1 + 1 > i; i++) {
            var p = R.findDotsAtSegment.apply(R, bez1.concat(i / n1));
            dots1.push({x: p.x, y: p.y, t: i / n1})
        }
        for (i = 0; n2 + 1 > i; i++) p = R.findDotsAtSegment.apply(R, bez2.concat(i / n2)), dots2.push({
            x: p.x,
            y: p.y,
            t: i / n2
        });
        for (i = 0; n1 > i; i++) for (var j = 0; n2 > j; j++) {
            var di = dots1[i], di1 = dots1[i + 1], dj = dots2[j], dj1 = dots2[j + 1],
                ci = abs(di1.x - di.x) < .001 ? "y" : "x", cj = abs(dj1.x - dj.x) < .001 ? "y" : "x",
                is = intersect(di.x, di.y, di1.x, di1.y, dj.x, dj.y, dj1.x, dj1.y);
            if (is) {
                if (xy[is.x.toFixed(4)] == is.y.toFixed(4)) continue;
                xy[is.x.toFixed(4)] = is.y.toFixed(4);
                var t1 = di.t + abs((is[ci] - di[ci]) / (di1[ci] - di[ci])) * (di1.t - di.t),
                    t2 = dj.t + abs((is[cj] - dj[cj]) / (dj1[cj] - dj[cj])) * (dj1.t - dj.t);
                t1 >= 0 && 1.001 >= t1 && t2 >= 0 && 1.001 >= t2 && (justCount ? res++ : res.push({
                    x: is.x,
                    y: is.y,
                    t1: mmin(t1, 1),
                    t2: mmin(t2, 1)
                }))
            }
        }
        return res
    }

    function interPathHelper(path1, path2, justCount) {
        path1 = R._path2curve(path1), path2 = R._path2curve(path2);
        for (var x1, y1, x2, y2, x1m, y1m, x2m, y2m, bez1, bez2, res = justCount ? 0 : [], i = 0, ii = path1.length; ii > i; i++) {
            var pi = path1[i];
            if ("M" == pi[0]) x1 = x1m = pi[1], y1 = y1m = pi[2]; else {
                "C" == pi[0] ? (bez1 = [x1, y1].concat(pi.slice(1)), x1 = bez1[6], y1 = bez1[7]) : (bez1 = [x1, y1, x1, y1, x1m, y1m, x1m, y1m], x1 = x1m, y1 = y1m);
                for (var j = 0, jj = path2.length; jj > j; j++) {
                    var pj = path2[j];
                    if ("M" == pj[0]) x2 = x2m = pj[1], y2 = y2m = pj[2]; else {
                        "C" == pj[0] ? (bez2 = [x2, y2].concat(pj.slice(1)), x2 = bez2[6], y2 = bez2[7]) : (bez2 = [x2, y2, x2, y2, x2m, y2m, x2m, y2m], x2 = x2m, y2 = y2m);
                        var intr = interHelper(bez1, bez2, justCount);
                        if (justCount) res += intr; else {
                            for (var k = 0, kk = intr.length; kk > k; k++) intr[k].segment1 = i, intr[k].segment2 = j, intr[k].bez1 = bez1, intr[k].bez2 = bez2;
                            res = res.concat(intr)
                        }
                    }
                }
            }
        }
        return res
    }

    function Matrix(a, b, c, d, e, f) {
        null != a ? (this.a = +a, this.b = +b, this.c = +c, this.d = +d, this.e = +e, this.f = +f) : (this.a = 1, this.b = 0, this.c = 0, this.d = 1, this.e = 0, this.f = 0)
    }

    function x_y_w_h() {
        return this.x + S + this.y + S + this.width + " 脳 " + this.height
    }

    function CubicBezierAtTime(t, p1x, p1y, p2x, p2y, duration) {
        function sampleCurveX(t) {
            return ((ax * t + bx) * t + cx) * t
        }

        function solve(x, epsilon) {
            var t = solveCurveX(x, epsilon);
            return ((ay * t + by) * t + cy) * t
        }

        function solveCurveX(x, epsilon) {
            var t0, t1, t2, x2, d2, i;
            for (t2 = x, i = 0; 8 > i; i++) {
                if (x2 = sampleCurveX(t2) - x, abs(x2) < epsilon) return t2;
                if (d2 = (3 * ax * t2 + 2 * bx) * t2 + cx, abs(d2) < 1e-6) break;
                t2 -= x2 / d2
            }
            if (t0 = 0, t1 = 1, t2 = x, t0 > t2) return t0;
            if (t2 > t1) return t1;
            for (; t1 > t0;) {
                if (x2 = sampleCurveX(t2), abs(x2 - x) < epsilon) return t2;
                x > x2 ? t0 = t2 : t1 = t2, t2 = (t1 - t0) / 2 + t0
            }
            return t2
        }

        var cx = 3 * p1x, bx = 3 * (p2x - p1x) - cx, ax = 1 - cx - bx, cy = 3 * p1y, by = 3 * (p2y - p1y) - cy,
            ay = 1 - cy - by;
        return solve(t, 1 / (200 * duration))
    }

    function Animation(anim, ms) {
        var percents = [], newAnim = {};
        if (this.ms = ms, this.times = 1, anim) {
            for (var attr in anim) anim[has](attr) && (newAnim[toFloat(attr)] = anim[attr], percents.push(toFloat(attr)));
            percents.sort(sortByNumber)
        }
        this.anim = newAnim, this.top = percents[percents.length - 1], this.percents = percents
    }

    function runAnimation(anim, element, percent, status, totalOrigin, times) {
        percent = toFloat(percent);
        var params, isInAnim, isInAnimSet, next, prev, timestamp, ms = anim.ms, from = {}, to = {}, diff = {};
        if (status) for (i = 0, ii = animationElements.length; ii > i; i++) {
            var e = animationElements[i];
            if (e.el.id == element.id && e.anim == anim) {
                e.percent != percent ? (animationElements.splice(i, 1), isInAnimSet = 1) : isInAnim = e, element.attr(e.totalOrigin);
                break
            }
        } else status = +to;
        for (var i = 0, ii = anim.percents.length; ii > i; i++) {
            if (anim.percents[i] == percent || anim.percents[i] > status * anim.top) {
                percent = anim.percents[i], prev = anim.percents[i - 1] || 0, ms = ms / anim.top * (percent - prev), next = anim.percents[i + 1], params = anim.anim[percent];
                break
            }
            status && element.attr(anim.anim[anim.percents[i]])
        }
        if (params) {
            if (isInAnim) isInAnim.initstatus = status, isInAnim.start = new Date - isInAnim.ms * status; else {
                for (var attr in params) if (params[has](attr) && (availableAnimAttrs[has](attr) || element.paper.customAttributes[has](attr))) switch (from[attr] = element.attr(attr), null == from[attr] && (from[attr] = availableAttrs[attr]), to[attr] = params[attr], availableAnimAttrs[attr]) {
                    case nu:
                        diff[attr] = (to[attr] - from[attr]) / ms;
                        break;
                    case"colour":
                        from[attr] = R.getRGB(from[attr]);
                        var toColour = R.getRGB(to[attr]);
                        diff[attr] = {
                            r: (toColour.r - from[attr].r) / ms,
                            g: (toColour.g - from[attr].g) / ms,
                            b: (toColour.b - from[attr].b) / ms
                        };
                        break;
                    case"path":
                        var pathes = path2curve(from[attr], to[attr]), toPath = pathes[1];
                        for (from[attr] = pathes[0], diff[attr] = [], i = 0, ii = from[attr].length; ii > i; i++) {
                            diff[attr][i] = [0];
                            for (var j = 1, jj = from[attr][i].length; jj > j; j++) diff[attr][i][j] = (toPath[i][j] - from[attr][i][j]) / ms
                        }
                        break;
                    case"transform":
                        var _ = element._, eq = equaliseTransform(_[attr], to[attr]);
                        if (eq) for (from[attr] = eq.from, to[attr] = eq.to, diff[attr] = [], diff[attr].real = !0, i = 0, ii = from[attr].length; ii > i; i++) for (diff[attr][i] = [from[attr][i][0]], j = 1, jj = from[attr][i].length; jj > j; j++) diff[attr][i][j] = (to[attr][i][j] - from[attr][i][j]) / ms; else {
                            var m = element.matrix || new Matrix, to2 = {
                                _: {transform: _.transform}, getBBox: function () {
                                    return element.getBBox(1)
                                }
                            };
                            from[attr] = [m.a, m.b, m.c, m.d, m.e, m.f], extractTransform(to2, to[attr]), to[attr] = to2._.transform, diff[attr] = [(to2.matrix.a - m.a) / ms, (to2.matrix.b - m.b) / ms, (to2.matrix.c - m.c) / ms, (to2.matrix.d - m.d) / ms, (to2.matrix.e - m.e) / ms, (to2.matrix.f - m.f) / ms]
                        }
                        break;
                    case"csv":
                        var values = Str(params[attr])[split](separator), from2 = Str(from[attr])[split](separator);
                        if ("clip-rect" == attr) for (from[attr] = from2, diff[attr] = [], i = from2.length; i--;) diff[attr][i] = (values[i] - from[attr][i]) / ms;
                        to[attr] = values;
                        break;
                    default:
                        for (values = [][concat](params[attr]), from2 = [][concat](from[attr]), diff[attr] = [], i = element.paper.customAttributes[attr].length; i--;) diff[attr][i] = ((values[i] || 0) - (from2[i] || 0)) / ms
                }
                var easing = params.easing, easyeasy = R.easing_formulas[easing];
                if (!easyeasy) if (easyeasy = Str(easing).match(bezierrg), easyeasy && 5 == easyeasy.length) {
                    var curve = easyeasy;
                    easyeasy = function (t) {
                        return CubicBezierAtTime(t, +curve[1], +curve[2], +curve[3], +curve[4], ms)
                    }
                } else easyeasy = pipe;
                if (timestamp = params.start || anim.start || +new Date, e = {
                    anim: anim,
                    percent: percent,
                    timestamp: timestamp,
                    start: timestamp + (anim.del || 0),
                    status: 0,
                    initstatus: status || 0,
                    stop: !1,
                    ms: ms,
                    easing: easyeasy,
                    from: from,
                    diff: diff,
                    to: to,
                    el: element,
                    callback: params.callback,
                    prev: prev,
                    next: next,
                    repeat: times || anim.times,
                    origin: element.attr(),
                    totalOrigin: totalOrigin
                }, animationElements.push(e), status && !isInAnim && !isInAnimSet && (e.stop = !0, e.start = new Date - ms * status, 1 == animationElements.length)) return animation();
                isInAnimSet && (e.start = new Date - e.ms * status), 1 == animationElements.length && requestAnimFrame(animation)
            }
            eve("raphael.anim.start." + element.id, element, anim)
        }
    }

    function stopAnimation(paper) {
        for (var i = 0; i < animationElements.length; i++) animationElements[i].el.paper == paper && animationElements.splice(i--, 1)
    }

    R.version = "2.1.2", R.eve = eve;
    var loaded, paperproto, separator = /[, ]+/,
        elements = {circle: 1, rect: 1, path: 1, ellipse: 1, text: 1, image: 1}, formatrg = /\{(\d+)\}/g,
        has = "hasOwnProperty", g = {doc: document, win: window},
        oldRaphael = {was: Object.prototype[has].call(g.win, "Raphael"), is: g.win.Raphael}, Paper = function () {
            this.ca = this.customAttributes = {}
        }, apply = "apply", concat = "concat",
        supportsTouch = "ontouchstart" in g.win || g.win.DocumentTouch && g.doc instanceof DocumentTouch, E = "",
        S = " ", Str = String, split = "split",
        events = "click dblclick mousedown mousemove mouseout mouseover mouseup touchstart touchmove touchend touchcancel"[split](S),
        touchMap = {mousedown: "touchstart", mousemove: "touchmove", mouseup: "touchend"},
        lowerCase = Str.prototype.toLowerCase, math = Math, mmax = math.max, mmin = math.min, abs = math.abs,
        pow = math.pow, PI = math.PI, nu = "number", string = "string", array = "array",
        objectToString = Object.prototype.toString,
        colourRegExp = (R._ISURL = /^url\(['"]?([^\)]+?)['"]?\)$/i, /^\s*((#[a-f\d]{6})|(#[a-f\d]{3})|rgba?\(\s*([\d\.]+%?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+%?(?:\s*,\s*[\d\.]+%?)?)\s*\)|hsba?\(\s*([\d\.]+(?:deg|\xb0|%)?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+(?:%?\s*,\s*[\d\.]+)?)%?\s*\)|hsla?\(\s*([\d\.]+(?:deg|\xb0|%)?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+(?:%?\s*,\s*[\d\.]+)?)%?\s*\))\s*$/i),
        isnan = {NaN: 1, Infinity: 1, "-Infinity": 1},
        bezierrg = /^(?:cubic-)?bezier\(([^,]+),([^,]+),([^,]+),([^\)]+)\)/, round = math.round, toFloat = parseFloat,
        toInt = parseInt, upperCase = Str.prototype.toUpperCase, availableAttrs = R._availableAttrs = {
            "arrow-end": "none",
            "arrow-start": "none",
            blur: 0,
            "clip-rect": "0 0 1e9 1e9",
            cursor: "default",
            cx: 0,
            cy: 0,
            fill: "#fff",
            "fill-opacity": 1,
            font: '10px "Arial"',
            "font-family": '"Arial"',
            "font-size": "10",
            "font-style": "normal",
            "font-weight": 400,
            gradient: 0,
            height: 0,
            href: "http://raphaeljs.com/",
            "letter-spacing": 0,
            opacity: 1,
            path: "M0,0",
            r: 0,
            rx: 0,
            ry: 0,
            src: "",
            stroke: "#000",
            "stroke-dasharray": "",
            "stroke-linecap": "butt",
            "stroke-linejoin": "butt",
            "stroke-miterlimit": 0,
            "stroke-opacity": 1,
            "stroke-width": 1,
            target: "_blank",
            "text-anchor": "middle",
            title: "Raphael",
            transform: "",
            width: 0,
            x: 0,
            y: 0
        }, availableAnimAttrs = R._availableAnimAttrs = {
            blur: nu,
            "clip-rect": "csv",
            cx: nu,
            cy: nu,
            fill: "colour",
            "fill-opacity": nu,
            "font-size": nu,
            height: nu,
            opacity: nu,
            path: "path",
            r: nu,
            rx: nu,
            ry: nu,
            stroke: "colour",
            "stroke-opacity": nu,
            "stroke-width": nu,
            transform: "transform",
            width: nu,
            x: nu,
            y: nu
        },
        commaSpaces = /[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*,[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*/,
        hsrg = {hs: 1, rg: 1}, p2s = /,?([achlmqrstvxz]),?/gi,
        pathCommand = /([achlmrqstvz])[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029,]*((-?\d*\.?\d*(?:e[\-+]?\d+)?[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*,?[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*)+)/gi,
        tCommand = /([rstm])[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029,]*((-?\d*\.?\d*(?:e[\-+]?\d+)?[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*,?[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*)+)/gi,
        pathValues = /(-?\d*\.?\d*(?:e[\-+]?\d+)?)[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*,?[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*/gi,
        eldata = (R._radial_gradient = /^r(?:\(([^,]+?)[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*,[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029]*([^\)]+?)\))?/, {}),
        sortByNumber = function (a, b) {
            return toFloat(a) - toFloat(b)
        }, fun = function () {
        }, pipe = function (x) {
            return x
        }, rectPath = R._rectPath = function (x, y, w, h, r) {
            return r ? [["M", x + r, y], ["l", w - 2 * r, 0], ["a", r, r, 0, 0, 1, r, r], ["l", 0, h - 2 * r], ["a", r, r, 0, 0, 1, -r, r], ["l", 2 * r - w, 0], ["a", r, r, 0, 0, 1, -r, -r], ["l", 0, 2 * r - h], ["a", r, r, 0, 0, 1, r, -r], ["z"]] : [["M", x, y], ["l", w, 0], ["l", 0, h], ["l", -w, 0], ["z"]]
        }, ellipsePath = function (x, y, rx, ry) {
            return null == ry && (ry = rx), [["M", x, y], ["m", 0, -ry], ["a", rx, ry, 0, 1, 1, 0, 2 * ry], ["a", rx, ry, 0, 1, 1, 0, -2 * ry], ["z"]]
        }, getPath = R._getPath = {
            path: function (el) {
                return el.attr("path")
            }, circle: function (el) {
                var a = el.attrs;
                return ellipsePath(a.cx, a.cy, a.r)
            }, ellipse: function (el) {
                var a = el.attrs;
                return ellipsePath(a.cx, a.cy, a.rx, a.ry)
            }, rect: function (el) {
                var a = el.attrs;
                return rectPath(a.x, a.y, a.width, a.height, a.r)
            }, image: function (el) {
                var a = el.attrs;
                return rectPath(a.x, a.y, a.width, a.height)
            }, text: function (el) {
                var bbox = el._getBBox();
                return rectPath(bbox.x, bbox.y, bbox.width, bbox.height)
            }, set: function (el) {
                var bbox = el._getBBox();
                return rectPath(bbox.x, bbox.y, bbox.width, bbox.height)
            }
        }, mapPath = R.mapPath = function (path, matrix) {
            if (!matrix) return path;
            var x, y, i, j, ii, jj, pathi;
            for (path = path2curve(path), i = 0, ii = path.length; ii > i; i++) for (pathi = path[i], j = 1, jj = pathi.length; jj > j; j += 2) x = matrix.x(pathi[j], pathi[j + 1]), y = matrix.y(pathi[j], pathi[j + 1]), pathi[j] = x, pathi[j + 1] = y;
            return path
        };
    if (R._g = g, R.type = g.win.SVGAngle || g.doc.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1") ? "SVG" : "VML", "VML" == R.type) {
        var b, d = g.doc.createElement("div");
        if (d.innerHTML = '<v:shape adj="1"/>', b = d.firstChild, b.style.behavior = "url(#default#VML)", !b || "object" != typeof b.adj) return R.type = E;
        d = null
    }
    R.svg = !(R.vml = "VML" == R.type), R._Paper = Paper, R.fn = paperproto = Paper.prototype = R.prototype, R._id = 0, R._oid = 0, R.is = function (o, type) {
        return type = lowerCase.call(type), "finite" == type ? !isnan[has](+o) : "array" == type ? o instanceof Array : "null" == type && null === o || type == typeof o && null !== o || "object" == type && o === Object(o) || "array" == type && Array.isArray && Array.isArray(o) || objectToString.call(o).slice(8, -1).toLowerCase() == type
    }, R.angle = function (x1, y1, x2, y2, x3, y3) {
        if (null == x3) {
            var x = x1 - x2, y = y1 - y2;
            return x || y ? (180 + 180 * math.atan2(-y, -x) / PI + 360) % 360 : 0
        }
        return R.angle(x1, y1, x3, y3) - R.angle(x2, y2, x3, y3)
    }, R.rad = function (deg) {
        return deg % 360 * PI / 180
    }, R.deg = function (rad) {
        return 180 * rad / PI % 360
    }, R.snapTo = function (values, value, tolerance) {
        if (tolerance = R.is(tolerance, "finite") ? tolerance : 10, R.is(values, array)) {
            for (var i = values.length; i--;) if (abs(values[i] - value) <= tolerance) return values[i]
        } else {
            values = +values;
            var rem = value % values;
            if (tolerance > rem) return value - rem;
            if (rem > values - tolerance) return value - rem + values
        }
        return value
    };
    R.createUUID = function (uuidRegEx, uuidReplacer) {
        return function () {
            return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(uuidRegEx, uuidReplacer).toUpperCase()
        }
    }(/[xy]/g, function (c) {
        var r = 16 * math.random() | 0, v = "x" == c ? r : 3 & r | 8;
        return v.toString(16)
    });
    R.setWindow = function (newwin) {
        eve("raphael.setWindow", R, g.win, newwin), g.win = newwin, g.doc = g.win.document, R._engine.initWin && R._engine.initWin(g.win)
    };
    var toHex = function (color) {
        if (R.vml) {
            var bod, trim = /^\s+|\s+$/g;
            try {
                var docum = new ActiveXObject("htmlfile");
                docum.write("<body>"), docum.close(), bod = docum.body
            } catch (e) {
                bod = createPopup().document.body
            }
            var range = bod.createTextRange();
            toHex = cacher(function (color) {
                try {
                    bod.style.color = Str(color).replace(trim, E);
                    var value = range.queryCommandValue("ForeColor");
                    return value = (255 & value) << 16 | 65280 & value | (16711680 & value) >>> 16, "#" + ("000000" + value.toString(16)).slice(-6)
                } catch (e) {
                    return "none"
                }
            })
        } else {
            var i = g.doc.createElement("i");
            i.title = "Rapha毛l Colour Picker", i.style.display = "none", g.doc.body.appendChild(i), toHex = cacher(function (color) {
                return i.style.color = color, g.doc.defaultView.getComputedStyle(i, E).getPropertyValue("color")
            })
        }
        return toHex(color)
    }, hsbtoString = function () {
        return "hsb(" + [this.h, this.s, this.b] + ")"
    }, hsltoString = function () {
        return "hsl(" + [this.h, this.s, this.l] + ")"
    }, rgbtoString = function () {
        return this.hex
    }, prepareRGB = function (r, g, b) {
        if (null == g && R.is(r, "object") && "r" in r && "g" in r && "b" in r && (b = r.b, g = r.g, r = r.r), null == g && R.is(r, string)) {
            var clr = R.getRGB(r);
            r = clr.r, g = clr.g, b = clr.b
        }
        return (r > 1 || g > 1 || b > 1) && (r /= 255, g /= 255, b /= 255), [r, g, b]
    }, packageRGB = function (r, g, b, o) {
        r *= 255, g *= 255, b *= 255;
        var rgb = {r: r, g: g, b: b, hex: R.rgb(r, g, b), toString: rgbtoString};
        return R.is(o, "finite") && (rgb.opacity = o), rgb
    };
    R.color = function (clr) {
        var rgb;
        return R.is(clr, "object") && "h" in clr && "s" in clr && "b" in clr ? (rgb = R.hsb2rgb(clr), clr.r = rgb.r, clr.g = rgb.g, clr.b = rgb.b, clr.hex = rgb.hex) : R.is(clr, "object") && "h" in clr && "s" in clr && "l" in clr ? (rgb = R.hsl2rgb(clr), clr.r = rgb.r, clr.g = rgb.g, clr.b = rgb.b, clr.hex = rgb.hex) : (R.is(clr, "string") && (clr = R.getRGB(clr)), R.is(clr, "object") && "r" in clr && "g" in clr && "b" in clr ? (rgb = R.rgb2hsl(clr), clr.h = rgb.h, clr.s = rgb.s, clr.l = rgb.l, rgb = R.rgb2hsb(clr), clr.v = rgb.b) : (clr = {hex: "none"}, clr.r = clr.g = clr.b = clr.h = clr.s = clr.v = clr.l = -1)), clr.toString = rgbtoString, clr
    }, R.hsb2rgb = function (h, s, v, o) {
        this.is(h, "object") && "h" in h && "s" in h && "b" in h && (v = h.b, s = h.s, h = h.h, o = h.o), h *= 360;
        var R, G, B, X, C;
        return h = h % 360 / 60, C = v * s, X = C * (1 - abs(h % 2 - 1)), R = G = B = v - C, h = ~~h, R += [C, X, 0, 0, X, C][h], G += [X, C, C, X, 0, 0][h], B += [0, 0, X, C, C, X][h], packageRGB(R, G, B, o)
    }, R.hsl2rgb = function (h, s, l, o) {
        this.is(h, "object") && "h" in h && "s" in h && "l" in h && (l = h.l, s = h.s, h = h.h), (h > 1 || s > 1 || l > 1) && (h /= 360, s /= 100, l /= 100), h *= 360;
        var R, G, B, X, C;
        return h = h % 360 / 60, C = 2 * s * (.5 > l ? l : 1 - l), X = C * (1 - abs(h % 2 - 1)), R = G = B = l - C / 2, h = ~~h, R += [C, X, 0, 0, X, C][h], G += [X, C, C, X, 0, 0][h], B += [0, 0, X, C, C, X][h], packageRGB(R, G, B, o)
    }, R.rgb2hsb = function (r, g, b) {
        b = prepareRGB(r, g, b), r = b[0], g = b[1], b = b[2];
        var H, S, V, C;
        return V = mmax(r, g, b), C = V - mmin(r, g, b), H = 0 == C ? null : V == r ? (g - b) / C : V == g ? (b - r) / C + 2 : (r - g) / C + 4, H = (H + 360) % 6 * 60 / 360, S = 0 == C ? 0 : C / V, {
            h: H,
            s: S,
            b: V,
            toString: hsbtoString
        }
    }, R.rgb2hsl = function (r, g, b) {
        b = prepareRGB(r, g, b), r = b[0], g = b[1], b = b[2];
        var H, S, L, M, m, C;
        return M = mmax(r, g, b), m = mmin(r, g, b), C = M - m, H = 0 == C ? null : M == r ? (g - b) / C : M == g ? (b - r) / C + 2 : (r - g) / C + 4, H = (H + 360) % 6 * 60 / 360, L = (M + m) / 2, S = 0 == C ? 0 : .5 > L ? C / (2 * L) : C / (2 - 2 * L), {
            h: H,
            s: S,
            l: L,
            toString: hsltoString
        }
    }, R._path2string = function () {
        return this.join(",").replace(p2s, "$1")
    };
    R._preload = function (src, f) {
        var img = g.doc.createElement("img");
        img.style.cssText = "position:absolute;left:-9999em;top:-9999em", img.onload = function () {
            f.call(this), this.onload = null, g.doc.body.removeChild(this)
        }, img.onerror = function () {
            g.doc.body.removeChild(this)
        }, g.doc.body.appendChild(img), img.src = src
    };
    R.getRGB = cacher(function (colour) {
        if (!colour || (colour = Str(colour)).indexOf("-") + 1) return {
            r: -1,
            g: -1,
            b: -1,
            hex: "none",
            error: 1,
            toString: clrToString
        };
        if ("none" == colour) return {r: -1, g: -1, b: -1, hex: "none", toString: clrToString};
        !(hsrg[has](colour.toLowerCase().substring(0, 2)) || "#" == colour.charAt()) && (colour = toHex(colour));
        var red, green, blue, opacity, t, values, rgb = colour.match(colourRegExp);
        return rgb ? (rgb[2] && (blue = toInt(rgb[2].substring(5), 16), green = toInt(rgb[2].substring(3, 5), 16), red = toInt(rgb[2].substring(1, 3), 16)), rgb[3] && (blue = toInt((t = rgb[3].charAt(3)) + t, 16), green = toInt((t = rgb[3].charAt(2)) + t, 16), red = toInt((t = rgb[3].charAt(1)) + t, 16)), rgb[4] && (values = rgb[4][split](commaSpaces), red = toFloat(values[0]), "%" == values[0].slice(-1) && (red *= 2.55), green = toFloat(values[1]), "%" == values[1].slice(-1) && (green *= 2.55), blue = toFloat(values[2]), "%" == values[2].slice(-1) && (blue *= 2.55), "rgba" == rgb[1].toLowerCase().slice(0, 4) && (opacity = toFloat(values[3])), values[3] && "%" == values[3].slice(-1) && (opacity /= 100)), rgb[5] ? (values = rgb[5][split](commaSpaces), red = toFloat(values[0]), "%" == values[0].slice(-1) && (red *= 2.55), green = toFloat(values[1]), "%" == values[1].slice(-1) && (green *= 2.55), blue = toFloat(values[2]), "%" == values[2].slice(-1) && (blue *= 2.55), ("deg" == values[0].slice(-3) || "掳" == values[0].slice(-1)) && (red /= 360), "hsba" == rgb[1].toLowerCase().slice(0, 4) && (opacity = toFloat(values[3])), values[3] && "%" == values[3].slice(-1) && (opacity /= 100), R.hsb2rgb(red, green, blue, opacity)) : rgb[6] ? (values = rgb[6][split](commaSpaces), red = toFloat(values[0]), "%" == values[0].slice(-1) && (red *= 2.55), green = toFloat(values[1]), "%" == values[1].slice(-1) && (green *= 2.55), blue = toFloat(values[2]), "%" == values[2].slice(-1) && (blue *= 2.55), ("deg" == values[0].slice(-3) || "掳" == values[0].slice(-1)) && (red /= 360), "hsla" == rgb[1].toLowerCase().slice(0, 4) && (opacity = toFloat(values[3])), values[3] && "%" == values[3].slice(-1) && (opacity /= 100), R.hsl2rgb(red, green, blue, opacity)) : (rgb = {
            r: red,
            g: green,
            b: blue,
            toString: clrToString
        }, rgb.hex = "#" + (16777216 | blue | green << 8 | red << 16).toString(16).slice(1), R.is(opacity, "finite") && (rgb.opacity = opacity), rgb)) : {
            r: -1,
            g: -1,
            b: -1,
            hex: "none",
            error: 1,
            toString: clrToString
        }
    }, R), R.hsb = cacher(function (h, s, b) {
        return R.hsb2rgb(h, s, b).hex
    }), R.hsl = cacher(function (h, s, l) {
        return R.hsl2rgb(h, s, l).hex
    }), R.rgb = cacher(function (r, g, b) {
        return "#" + (16777216 | b | g << 8 | r << 16).toString(16).slice(1)
    }), R.getColor = function (value) {
        var start = this.getColor.start = this.getColor.start || {h: 0, s: 1, b: value || .75},
            rgb = this.hsb2rgb(start.h, start.s, start.b);
        return start.h += .075, start.h > 1 && (start.h = 0, start.s -= .2, start.s <= 0 && (this.getColor.start = {
            h: 0,
            s: 1,
            b: start.b
        })), rgb.hex
    }, R.getColor.reset = function () {
        delete this.start
    }, R.parsePathString = function (pathString) {
        if (!pathString) return null;
        var pth = paths(pathString);
        if (pth.arr) return pathClone(pth.arr);
        var paramCounts = {a: 7, c: 6, h: 1, l: 2, m: 2, r: 4, q: 4, s: 4, t: 2, v: 1, z: 0}, data = [];
        return R.is(pathString, array) && R.is(pathString[0], array) && (data = pathClone(pathString)), data.length || Str(pathString).replace(pathCommand, function (a, b, c) {
            var params = [], name = b.toLowerCase();
            if (c.replace(pathValues, function (a, b) {
                b && params.push(+b)
            }), "m" == name && params.length > 2 && (data.push([b][concat](params.splice(0, 2))), name = "l", b = "m" == b ? "l" : "L"), "r" == name) data.push([b][concat](params)); else for (; params.length >= paramCounts[name] && (data.push([b][concat](params.splice(0, paramCounts[name]))), paramCounts[name]);) ;
        }), data.toString = R._path2string, pth.arr = pathClone(data), data
    }, R.parseTransformString = cacher(function (TString) {
        if (!TString) return null;
        var data = [];
        return R.is(TString, array) && R.is(TString[0], array) && (data = pathClone(TString)), data.length || Str(TString).replace(tCommand, function (a, b, c) {
            {
                var params = [];
                lowerCase.call(b)
            }
            c.replace(pathValues, function (a, b) {
                b && params.push(+b)
            }), data.push([b][concat](params))
        }), data.toString = R._path2string, data
    });
    var paths = function (ps) {
        var p = paths.ps = paths.ps || {};
        return p[ps] ? p[ps].sleep = 100 : p[ps] = {sleep: 100}, setTimeout(function () {
            for (var key in p) p[has](key) && key != ps && (p[key].sleep--, !p[key].sleep && delete p[key])
        }), p[ps]
    };
    R.findDotsAtSegment = function (p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, t) {
        var t1 = 1 - t, t13 = pow(t1, 3), t12 = pow(t1, 2), t2 = t * t, t3 = t2 * t,
            x = t13 * p1x + 3 * t12 * t * c1x + 3 * t1 * t * t * c2x + t3 * p2x,
            y = t13 * p1y + 3 * t12 * t * c1y + 3 * t1 * t * t * c2y + t3 * p2y,
            mx = p1x + 2 * t * (c1x - p1x) + t2 * (c2x - 2 * c1x + p1x),
            my = p1y + 2 * t * (c1y - p1y) + t2 * (c2y - 2 * c1y + p1y),
            nx = c1x + 2 * t * (c2x - c1x) + t2 * (p2x - 2 * c2x + c1x),
            ny = c1y + 2 * t * (c2y - c1y) + t2 * (p2y - 2 * c2y + c1y), ax = t1 * p1x + t * c1x,
            ay = t1 * p1y + t * c1y, cx = t1 * c2x + t * p2x, cy = t1 * c2y + t * p2y,
            alpha = 90 - 180 * math.atan2(mx - nx, my - ny) / PI;
        return (mx > nx || ny > my) && (alpha += 180), {
            x: x,
            y: y,
            m: {x: mx, y: my},
            n: {x: nx, y: ny},
            start: {x: ax, y: ay},
            end: {x: cx, y: cy},
            alpha: alpha
        }
    }, R.bezierBBox = function (p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y) {
        R.is(p1x, "array") || (p1x = [p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y]);
        var bbox = curveDim.apply(null, p1x);
        return {
            x: bbox.min.x,
            y: bbox.min.y,
            x2: bbox.max.x,
            y2: bbox.max.y,
            width: bbox.max.x - bbox.min.x,
            height: bbox.max.y - bbox.min.y
        }
    }, R.isPointInsideBBox = function (bbox, x, y) {
        return x >= bbox.x && x <= bbox.x2 && y >= bbox.y && y <= bbox.y2
    }, R.isBBoxIntersect = function (bbox1, bbox2) {
        var i = R.isPointInsideBBox;
        return i(bbox2, bbox1.x, bbox1.y) || i(bbox2, bbox1.x2, bbox1.y) || i(bbox2, bbox1.x, bbox1.y2) || i(bbox2, bbox1.x2, bbox1.y2) || i(bbox1, bbox2.x, bbox2.y) || i(bbox1, bbox2.x2, bbox2.y) || i(bbox1, bbox2.x, bbox2.y2) || i(bbox1, bbox2.x2, bbox2.y2) || (bbox1.x < bbox2.x2 && bbox1.x > bbox2.x || bbox2.x < bbox1.x2 && bbox2.x > bbox1.x) && (bbox1.y < bbox2.y2 && bbox1.y > bbox2.y || bbox2.y < bbox1.y2 && bbox2.y > bbox1.y)
    }, R.pathIntersection = function (path1, path2) {
        return interPathHelper(path1, path2)
    }, R.pathIntersectionNumber = function (path1, path2) {
        return interPathHelper(path1, path2, 1)
    }, R.isPointInsidePath = function (path, x, y) {
        var bbox = R.pathBBox(path);
        return R.isPointInsideBBox(bbox, x, y) && interPathHelper(path, [["M", x, y], ["H", bbox.x2 + 10]], 1) % 2 == 1
    }, R._removedFactory = function (methodname) {
        return function () {
            eve("raphael.log", null, "Rapha毛l: you are calling to method 鈥�" + methodname + "鈥� of removed object", methodname)
        }
    };
    var pathDimensions = R.pathBBox = function (path) {
        var pth = paths(path);
        if (pth.bbox) return clone(pth.bbox);
        if (!path) return {x: 0, y: 0, width: 0, height: 0, x2: 0, y2: 0};
        path = path2curve(path);
        for (var p, x = 0, y = 0, X = [], Y = [], i = 0, ii = path.length; ii > i; i++) if (p = path[i], "M" == p[0]) x = p[1], y = p[2], X.push(x), Y.push(y); else {
            var dim = curveDim(x, y, p[1], p[2], p[3], p[4], p[5], p[6]);
            X = X[concat](dim.min.x, dim.max.x), Y = Y[concat](dim.min.y, dim.max.y), x = p[5], y = p[6]
        }
        var xmin = mmin[apply](0, X), ymin = mmin[apply](0, Y), xmax = mmax[apply](0, X), ymax = mmax[apply](0, Y),
            width = xmax - xmin, height = ymax - ymin, bb = {
                x: xmin,
                y: ymin,
                x2: xmax,
                y2: ymax,
                width: width,
                height: height,
                cx: xmin + width / 2,
                cy: ymin + height / 2
            };
        return pth.bbox = clone(bb), bb
    }, pathClone = function (pathArray) {
        var res = clone(pathArray);
        return res.toString = R._path2string, res
    }, pathToRelative = R._pathToRelative = function (pathArray) {
        var pth = paths(pathArray);
        if (pth.rel) return pathClone(pth.rel);
        R.is(pathArray, array) && R.is(pathArray && pathArray[0], array) || (pathArray = R.parsePathString(pathArray));
        var res = [], x = 0, y = 0, mx = 0, my = 0, start = 0;
        "M" == pathArray[0][0] && (x = pathArray[0][1], y = pathArray[0][2], mx = x, my = y, start++, res.push(["M", x, y]));
        for (var i = start, ii = pathArray.length; ii > i; i++) {
            var r = res[i] = [], pa = pathArray[i];
            if (pa[0] != lowerCase.call(pa[0])) switch (r[0] = lowerCase.call(pa[0]), r[0]) {
                case"a":
                    r[1] = pa[1], r[2] = pa[2], r[3] = pa[3], r[4] = pa[4], r[5] = pa[5], r[6] = +(pa[6] - x).toFixed(3), r[7] = +(pa[7] - y).toFixed(3);
                    break;
                case"v":
                    r[1] = +(pa[1] - y).toFixed(3);
                    break;
                case"m":
                    mx = pa[1], my = pa[2];
                default:
                    for (var j = 1, jj = pa.length; jj > j; j++) r[j] = +(pa[j] - (j % 2 ? x : y)).toFixed(3)
            } else {
                r = res[i] = [], "m" == pa[0] && (mx = pa[1] + x, my = pa[2] + y);
                for (var k = 0, kk = pa.length; kk > k; k++) res[i][k] = pa[k]
            }
            var len = res[i].length;
            switch (res[i][0]) {
                case"z":
                    x = mx, y = my;
                    break;
                case"h":
                    x += +res[i][len - 1];
                    break;
                case"v":
                    y += +res[i][len - 1];
                    break;
                default:
                    x += +res[i][len - 2], y += +res[i][len - 1]
            }
        }
        return res.toString = R._path2string, pth.rel = pathClone(res), res
    }, pathToAbsolute = R._pathToAbsolute = function (pathArray) {
        var pth = paths(pathArray);
        if (pth.abs) return pathClone(pth.abs);
        if (R.is(pathArray, array) && R.is(pathArray && pathArray[0], array) || (pathArray = R.parsePathString(pathArray)), !pathArray || !pathArray.length) return [["M", 0, 0]];
        var res = [], x = 0, y = 0, mx = 0, my = 0, start = 0;
        "M" == pathArray[0][0] && (x = +pathArray[0][1], y = +pathArray[0][2], mx = x, my = y, start++, res[0] = ["M", x, y]);
        for (var r, pa, crz = 3 == pathArray.length && "M" == pathArray[0][0] && "R" == pathArray[1][0].toUpperCase() && "Z" == pathArray[2][0].toUpperCase(), i = start, ii = pathArray.length; ii > i; i++) {
            if (res.push(r = []), pa = pathArray[i], pa[0] != upperCase.call(pa[0])) switch (r[0] = upperCase.call(pa[0]), r[0]) {
                case"A":
                    r[1] = pa[1], r[2] = pa[2], r[3] = pa[3], r[4] = pa[4], r[5] = pa[5], r[6] = +(pa[6] + x), r[7] = +(pa[7] + y);
                    break;
                case"V":
                    r[1] = +pa[1] + y;
                    break;
                case"H":
                    r[1] = +pa[1] + x;
                    break;
                case"R":
                    for (var dots = [x, y][concat](pa.slice(1)), j = 2, jj = dots.length; jj > j; j++) dots[j] = +dots[j] + x, dots[++j] = +dots[j] + y;
                    res.pop(), res = res[concat](catmullRom2bezier(dots, crz));
                    break;
                case"M":
                    mx = +pa[1] + x, my = +pa[2] + y;
                default:
                    for (j = 1, jj = pa.length; jj > j; j++) r[j] = +pa[j] + (j % 2 ? x : y)
            } else if ("R" == pa[0]) dots = [x, y][concat](pa.slice(1)), res.pop(), res = res[concat](catmullRom2bezier(dots, crz)), r = ["R"][concat](pa.slice(-2)); else for (var k = 0, kk = pa.length; kk > k; k++) r[k] = pa[k];
            switch (r[0]) {
                case"Z":
                    x = mx, y = my;
                    break;
                case"H":
                    x = r[1];
                    break;
                case"V":
                    y = r[1];
                    break;
                case"M":
                    mx = r[r.length - 2], my = r[r.length - 1];
                default:
                    x = r[r.length - 2], y = r[r.length - 1]
            }
        }
        return res.toString = R._path2string, pth.abs = pathClone(res), res
    }, l2c = function (x1, y1, x2, y2) {
        return [x1, y1, x2, y2, x2, y2]
    }, q2c = function (x1, y1, ax, ay, x2, y2) {
        var _13 = 1 / 3, _23 = 2 / 3;
        return [_13 * x1 + _23 * ax, _13 * y1 + _23 * ay, _13 * x2 + _23 * ax, _13 * y2 + _23 * ay, x2, y2]
    }, a2c = function (x1, y1, rx, ry, angle, large_arc_flag, sweep_flag, x2, y2, recursive) {
        var xy, _120 = 120 * PI / 180, rad = PI / 180 * (+angle || 0), res = [], rotate = cacher(function (x, y, rad) {
            var X = x * math.cos(rad) - y * math.sin(rad), Y = x * math.sin(rad) + y * math.cos(rad);
            return {x: X, y: Y}
        });
        if (recursive) f1 = recursive[0], f2 = recursive[1], cx = recursive[2], cy = recursive[3]; else {
            xy = rotate(x1, y1, -rad), x1 = xy.x, y1 = xy.y, xy = rotate(x2, y2, -rad), x2 = xy.x, y2 = xy.y;
            var x = (math.cos(PI / 180 * angle), math.sin(PI / 180 * angle), (x1 - x2) / 2), y = (y1 - y2) / 2,
                h = x * x / (rx * rx) + y * y / (ry * ry);
            h > 1 && (h = math.sqrt(h), rx = h * rx, ry = h * ry);
            var rx2 = rx * rx, ry2 = ry * ry,
                k = (large_arc_flag == sweep_flag ? -1 : 1) * math.sqrt(abs((rx2 * ry2 - rx2 * y * y - ry2 * x * x) / (rx2 * y * y + ry2 * x * x))),
                cx = k * rx * y / ry + (x1 + x2) / 2, cy = k * -ry * x / rx + (y1 + y2) / 2,
                f1 = math.asin(((y1 - cy) / ry).toFixed(9)), f2 = math.asin(((y2 - cy) / ry).toFixed(9));
            f1 = cx > x1 ? PI - f1 : f1, f2 = cx > x2 ? PI - f2 : f2, 0 > f1 && (f1 = 2 * PI + f1), 0 > f2 && (f2 = 2 * PI + f2), sweep_flag && f1 > f2 && (f1 -= 2 * PI), !sweep_flag && f2 > f1 && (f2 -= 2 * PI)
        }
        var df = f2 - f1;
        if (abs(df) > _120) {
            var f2old = f2, x2old = x2, y2old = y2;
            f2 = f1 + _120 * (sweep_flag && f2 > f1 ? 1 : -1), x2 = cx + rx * math.cos(f2), y2 = cy + ry * math.sin(f2), res = a2c(x2, y2, rx, ry, angle, 0, sweep_flag, x2old, y2old, [f2, f2old, cx, cy])
        }
        df = f2 - f1;
        var c1 = math.cos(f1), s1 = math.sin(f1), c2 = math.cos(f2), s2 = math.sin(f2), t = math.tan(df / 4),
            hx = 4 / 3 * rx * t, hy = 4 / 3 * ry * t, m1 = [x1, y1], m2 = [x1 + hx * s1, y1 - hy * c1],
            m3 = [x2 + hx * s2, y2 - hy * c2], m4 = [x2, y2];
        if (m2[0] = 2 * m1[0] - m2[0], m2[1] = 2 * m1[1] - m2[1], recursive) return [m2, m3, m4][concat](res);
        res = [m2, m3, m4][concat](res).join()[split](",");
        for (var newres = [], i = 0, ii = res.length; ii > i; i++) newres[i] = i % 2 ? rotate(res[i - 1], res[i], rad).y : rotate(res[i], res[i + 1], rad).x;
        return newres
    }, findDotAtSegment = function (p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, t) {
        var t1 = 1 - t;
        return {
            x: pow(t1, 3) * p1x + 3 * pow(t1, 2) * t * c1x + 3 * t1 * t * t * c2x + pow(t, 3) * p2x,
            y: pow(t1, 3) * p1y + 3 * pow(t1, 2) * t * c1y + 3 * t1 * t * t * c2y + pow(t, 3) * p2y
        }
    }, curveDim = cacher(function (p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y) {
        var dot, a = c2x - 2 * c1x + p1x - (p2x - 2 * c2x + c1x), b = 2 * (c1x - p1x) - 2 * (c2x - c1x), c = p1x - c1x,
            t1 = (-b + math.sqrt(b * b - 4 * a * c)) / 2 / a, t2 = (-b - math.sqrt(b * b - 4 * a * c)) / 2 / a,
            y = [p1y, p2y], x = [p1x, p2x];
        return abs(t1) > "1e12" && (t1 = .5), abs(t2) > "1e12" && (t2 = .5), t1 > 0 && 1 > t1 && (dot = findDotAtSegment(p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, t1), x.push(dot.x), y.push(dot.y)), t2 > 0 && 1 > t2 && (dot = findDotAtSegment(p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, t2), x.push(dot.x), y.push(dot.y)), a = c2y - 2 * c1y + p1y - (p2y - 2 * c2y + c1y), b = 2 * (c1y - p1y) - 2 * (c2y - c1y), c = p1y - c1y, t1 = (-b + math.sqrt(b * b - 4 * a * c)) / 2 / a, t2 = (-b - math.sqrt(b * b - 4 * a * c)) / 2 / a, abs(t1) > "1e12" && (t1 = .5), abs(t2) > "1e12" && (t2 = .5), t1 > 0 && 1 > t1 && (dot = findDotAtSegment(p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, t1), x.push(dot.x), y.push(dot.y)), t2 > 0 && 1 > t2 && (dot = findDotAtSegment(p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, t2), x.push(dot.x), y.push(dot.y)), {
            min: {
                x: mmin[apply](0, x),
                y: mmin[apply](0, y)
            }, max: {x: mmax[apply](0, x), y: mmax[apply](0, y)}
        }
    }), path2curve = R._path2curve = cacher(function (path, path2) {
        var pth = !path2 && paths(path);
        if (!path2 && pth.curve) return pathClone(pth.curve);
        for (var p = pathToAbsolute(path), p2 = path2 && pathToAbsolute(path2), attrs = {
            x: 0,
            y: 0,
            bx: 0,
            by: 0,
            X: 0,
            Y: 0,
            qx: null,
            qy: null
        }, attrs2 = {
            x: 0,
            y: 0,
            bx: 0,
            by: 0,
            X: 0,
            Y: 0,
            qx: null,
            qy: null
        }, processPath = (function (path, d, pcom) {
            var nx, ny;
            if (!path) return ["C", d.x, d.y, d.x, d.y, d.x, d.y];
            switch (!(path[0] in {T: 1, Q: 1}) && (d.qx = d.qy = null), path[0]) {
                case"M":
                    d.X = path[1], d.Y = path[2];
                    break;
                case"A":
                    path = ["C"][concat](a2c[apply](0, [d.x, d.y][concat](path.slice(1))));
                    break;
                case"S":
                    "C" == pcom || "S" == pcom ? (nx = 2 * d.x - d.bx, ny = 2 * d.y - d.by) : (nx = d.x, ny = d.y), path = ["C", nx, ny][concat](path.slice(1));
                    break;
                case"T":
                    "Q" == pcom || "T" == pcom ? (d.qx = 2 * d.x - d.qx, d.qy = 2 * d.y - d.qy) : (d.qx = d.x, d.qy = d.y), path = ["C"][concat](q2c(d.x, d.y, d.qx, d.qy, path[1], path[2]));
                    break;
                case"Q":
                    d.qx = path[1], d.qy = path[2], path = ["C"][concat](q2c(d.x, d.y, path[1], path[2], path[3], path[4]));
                    break;
                case"L":
                    path = ["C"][concat](l2c(d.x, d.y, path[1], path[2]));
                    break;
                case"H":
                    path = ["C"][concat](l2c(d.x, d.y, path[1], d.y));
                    break;
                case"V":
                    path = ["C"][concat](l2c(d.x, d.y, d.x, path[1]));
                    break;
                case"Z":
                    path = ["C"][concat](l2c(d.x, d.y, d.X, d.Y))
            }
            return path
        }), fixArc = function (pp, i) {
            if (pp[i].length > 7) {
                pp[i].shift();
                for (var pi = pp[i]; pi.length;) pp.splice(i++, 0, ["C"][concat](pi.splice(0, 6)));
                pp.splice(i, 1), ii = mmax(p.length, p2 && p2.length || 0)
            }
        }, fixM = function (path1, path2, a1, a2, i) {
            path1 && path2 && "M" == path1[i][0] && "M" != path2[i][0] && (path2.splice(i, 0, ["M", a2.x, a2.y]), a1.bx = 0, a1.by = 0, a1.x = path1[i][1], a1.y = path1[i][2], ii = mmax(p.length, p2 && p2.length || 0))
        }, i = 0, ii = mmax(p.length, p2 && p2.length || 0); ii > i; i++) {
            p[i] = processPath(p[i], attrs), fixArc(p, i), p2 && (p2[i] = processPath(p2[i], attrs2)), p2 && fixArc(p2, i), fixM(p, p2, attrs, attrs2, i), fixM(p2, p, attrs2, attrs, i);
            var seg = p[i], seg2 = p2 && p2[i], seglen = seg.length, seg2len = p2 && seg2.length;
            attrs.x = seg[seglen - 2], attrs.y = seg[seglen - 1], attrs.bx = toFloat(seg[seglen - 4]) || attrs.x, attrs.by = toFloat(seg[seglen - 3]) || attrs.y, attrs2.bx = p2 && (toFloat(seg2[seg2len - 4]) || attrs2.x), attrs2.by = p2 && (toFloat(seg2[seg2len - 3]) || attrs2.y), attrs2.x = p2 && seg2[seg2len - 2], attrs2.y = p2 && seg2[seg2len - 1]
        }
        return p2 || (pth.curve = pathClone(p)), p2 ? [p, p2] : p
    }, null, pathClone), tear = (R._parseDots = cacher(function (gradient) {
        for (var dots = [], i = 0, ii = gradient.length; ii > i; i++) {
            var dot = {}, par = gradient[i].match(/^([^:]*):?([\d\.]*)/);
            if (dot.color = R.getRGB(par[1]), dot.color.error) return null;
            dot.color = dot.color.hex, par[2] && (dot.offset = par[2] + "%"), dots.push(dot)
        }
        for (i = 1, ii = dots.length - 1; ii > i; i++) if (!dots[i].offset) {
            for (var start = toFloat(dots[i - 1].offset || 0), end = 0, j = i + 1; ii > j; j++) if (dots[j].offset) {
                end = dots[j].offset;
                break
            }
            end || (end = 100, j = ii), end = toFloat(end);
            for (var d = (end - start) / (j - i + 1); j > i; i++) start += d, dots[i].offset = start + "%"
        }
        return dots
    }), R._tear = function (el, paper) {
        el == paper.top && (paper.top = el.prev), el == paper.bottom && (paper.bottom = el.next), el.next && (el.next.prev = el.prev), el.prev && (el.prev.next = el.next)
    }), toMatrix = (R._tofront = function (el, paper) {
        paper.top !== el && (tear(el, paper), el.next = null, el.prev = paper.top, paper.top.next = el, paper.top = el)
    }, R._toback = function (el, paper) {
        paper.bottom !== el && (tear(el, paper), el.next = paper.bottom, el.prev = null, paper.bottom.prev = el, paper.bottom = el)
    }, R._insertafter = function (el, el2, paper) {
        tear(el, paper), el2 == paper.top && (paper.top = el), el2.next && (el2.next.prev = el), el.next = el2.next, el.prev = el2, el2.next = el
    }, R._insertbefore = function (el, el2, paper) {
        tear(el, paper), el2 == paper.bottom && (paper.bottom = el), el2.prev && (el2.prev.next = el), el.prev = el2.prev, el2.prev = el, el.next = el2
    }, R.toMatrix = function (path, transform) {
        var bb = pathDimensions(path), el = {
            _: {transform: E}, getBBox: function () {
                return bb
            }
        };
        return extractTransform(el, transform), el.matrix
    }), extractTransform = (R.transformPath = function (path, transform) {
        return mapPath(path, toMatrix(path, transform))
    }, R._extractTransform = function (el, tstr) {
        if (null == tstr) return el._.transform;
        tstr = Str(tstr).replace(/\.{3}|\u2026/g, el._.transform || E);
        var tdata = R.parseTransformString(tstr), deg = 0, dx = 0, dy = 0, sx = 1, sy = 1, _ = el._, m = new Matrix;
        if (_.transform = tdata || [], tdata) for (var i = 0, ii = tdata.length; ii > i; i++) {
            var x1, y1, x2, y2, bb, t = tdata[i], tlen = t.length, command = Str(t[0]).toLowerCase(),
                absolute = t[0] != command, inver = absolute ? m.invert() : 0;
            "t" == command && 3 == tlen ? absolute ? (x1 = inver.x(0, 0), y1 = inver.y(0, 0), x2 = inver.x(t[1], t[2]), y2 = inver.y(t[1], t[2]), m.translate(x2 - x1, y2 - y1)) : m.translate(t[1], t[2]) : "r" == command ? 2 == tlen ? (bb = bb || el.getBBox(1), m.rotate(t[1], bb.x + bb.width / 2, bb.y + bb.height / 2), deg += t[1]) : 4 == tlen && (absolute ? (x2 = inver.x(t[2], t[3]), y2 = inver.y(t[2], t[3]), m.rotate(t[1], x2, y2)) : m.rotate(t[1], t[2], t[3]), deg += t[1]) : "s" == command ? 2 == tlen || 3 == tlen ? (bb = bb || el.getBBox(1), m.scale(t[1], t[tlen - 1], bb.x + bb.width / 2, bb.y + bb.height / 2), sx *= t[1], sy *= t[tlen - 1]) : 5 == tlen && (absolute ? (x2 = inver.x(t[3], t[4]), y2 = inver.y(t[3], t[4]), m.scale(t[1], t[2], x2, y2)) : m.scale(t[1], t[2], t[3], t[4]), sx *= t[1], sy *= t[2]) : "m" == command && 7 == tlen && m.add(t[1], t[2], t[3], t[4], t[5], t[6]), _.dirtyT = 1, el.matrix = m
        }
        el.matrix = m, _.sx = sx, _.sy = sy, _.deg = deg, _.dx = dx = m.e, _.dy = dy = m.f, 1 == sx && 1 == sy && !deg && _.bbox ? (_.bbox.x += +dx, _.bbox.y += +dy) : _.dirtyT = 1
    }), getEmpty = function (item) {
        var l = item[0];
        switch (l.toLowerCase()) {
            case"t":
                return [l, 0, 0];
            case"m":
                return [l, 1, 0, 0, 1, 0, 0];
            case"r":
                return 4 == item.length ? [l, 0, item[2], item[3]] : [l, 0];
            case"s":
                return 5 == item.length ? [l, 1, 1, item[3], item[4]] : 3 == item.length ? [l, 1, 1] : [l, 1]
        }
    }, equaliseTransform = R._equaliseTransform = function (t1, t2) {
        t2 = Str(t2).replace(/\.{3}|\u2026/g, t1), t1 = R.parseTransformString(t1) || [], t2 = R.parseTransformString(t2) || [];
        for (var j, jj, tt1, tt2, maxlength = mmax(t1.length, t2.length), from = [], to = [], i = 0; maxlength > i; i++) {
            if (tt1 = t1[i] || getEmpty(t2[i]), tt2 = t2[i] || getEmpty(tt1), tt1[0] != tt2[0] || "r" == tt1[0].toLowerCase() && (tt1[2] != tt2[2] || tt1[3] != tt2[3]) || "s" == tt1[0].toLowerCase() && (tt1[3] != tt2[3] || tt1[4] != tt2[4])) return;
            for (from[i] = [], to[i] = [], j = 0, jj = mmax(tt1.length, tt2.length); jj > j; j++) j in tt1 && (from[i][j] = tt1[j]), j in tt2 && (to[i][j] = tt2[j])
        }
        return {from: from, to: to}
    };
    R._getContainer = function (x, y, w, h) {
        var container;
        return container = null != h || R.is(x, "object") ? x : g.doc.getElementById(x), null != container ? container.tagName ? null == y ? {
            container: container,
            width: container.style.pixelWidth || container.offsetWidth,
            height: container.style.pixelHeight || container.offsetHeight
        } : {container: container, width: y, height: w} : {container: 1, x: x, y: y, width: w, height: h} : void 0
    }, R.pathToRelative = pathToRelative, R._engine = {}, R.path2curve = path2curve, R.matrix = function (a, b, c, d, e, f) {
        return new Matrix(a, b, c, d, e, f)
    }, function (matrixproto) {
        function norm(a) {
            return a[0] * a[0] + a[1] * a[1]
        }

        function normalize(a) {
            var mag = math.sqrt(norm(a));
            a[0] && (a[0] /= mag), a[1] && (a[1] /= mag)
        }

        matrixproto.add = function (a, b, c, d, e, f) {
            var x, y, z, res, out = [[], [], []], m = [[this.a, this.c, this.e], [this.b, this.d, this.f], [0, 0, 1]],
                matrix = [[a, c, e], [b, d, f], [0, 0, 1]];
            for (a && a instanceof Matrix && (matrix = [[a.a, a.c, a.e], [a.b, a.d, a.f], [0, 0, 1]]), x = 0; 3 > x; x++) for (y = 0; 3 > y; y++) {
                for (res = 0, z = 0; 3 > z; z++) res += m[x][z] * matrix[z][y];
                out[x][y] = res
            }
            this.a = out[0][0], this.b = out[1][0], this.c = out[0][1], this.d = out[1][1], this.e = out[0][2], this.f = out[1][2]
        }, matrixproto.invert = function () {
            var me = this, x = me.a * me.d - me.b * me.c;
            return new Matrix(me.d / x, -me.b / x, -me.c / x, me.a / x, (me.c * me.f - me.d * me.e) / x, (me.b * me.e - me.a * me.f) / x)
        }, matrixproto.clone = function () {
            return new Matrix(this.a, this.b, this.c, this.d, this.e, this.f)
        }, matrixproto.translate = function (x, y) {
            this.add(1, 0, 0, 1, x, y)
        }, matrixproto.scale = function (x, y, cx, cy) {
            null == y && (y = x), (cx || cy) && this.add(1, 0, 0, 1, cx, cy), this.add(x, 0, 0, y, 0, 0), (cx || cy) && this.add(1, 0, 0, 1, -cx, -cy)
        }, matrixproto.rotate = function (a, x, y) {
            a = R.rad(a), x = x || 0, y = y || 0;
            var cos = +math.cos(a).toFixed(9), sin = +math.sin(a).toFixed(9);
            this.add(cos, sin, -sin, cos, x, y), this.add(1, 0, 0, 1, -x, -y)
        }, matrixproto.x = function (x, y) {
            return x * this.a + y * this.c + this.e
        }, matrixproto.y = function (x, y) {
            return x * this.b + y * this.d + this.f
        }, matrixproto.get = function (i) {
            return +this[Str.fromCharCode(97 + i)].toFixed(4)
        }, matrixproto.toString = function () {
            return R.svg ? "matrix(" + [this.get(0), this.get(1), this.get(2), this.get(3), this.get(4), this.get(5)].join() + ")" : [this.get(0), this.get(2), this.get(1), this.get(3), 0, 0].join()
        }, matrixproto.toFilter = function () {
            return "progid:DXImageTransform.Microsoft.Matrix(M11=" + this.get(0) + ", M12=" + this.get(2) + ", M21=" + this.get(1) + ", M22=" + this.get(3) + ", Dx=" + this.get(4) + ", Dy=" + this.get(5) + ", sizingmethod='auto expand')"
        }, matrixproto.offset = function () {
            return [this.e.toFixed(4), this.f.toFixed(4)]
        }, matrixproto.split = function () {
            var out = {};
            out.dx = this.e, out.dy = this.f;
            var row = [[this.a, this.c], [this.b, this.d]];
            out.scalex = math.sqrt(norm(row[0])), normalize(row[0]), out.shear = row[0][0] * row[1][0] + row[0][1] * row[1][1], row[1] = [row[1][0] - row[0][0] * out.shear, row[1][1] - row[0][1] * out.shear], out.scaley = math.sqrt(norm(row[1])), normalize(row[1]), out.shear /= out.scaley;
            var sin = -row[0][1], cos = row[1][1];
            return 0 > cos ? (out.rotate = R.deg(math.acos(cos)), 0 > sin && (out.rotate = 360 - out.rotate)) : out.rotate = R.deg(math.asin(sin)), out.isSimple = !(+out.shear.toFixed(9) || out.scalex.toFixed(9) != out.scaley.toFixed(9) && out.rotate), out.isSuperSimple = !+out.shear.toFixed(9) && out.scalex.toFixed(9) == out.scaley.toFixed(9) && !out.rotate, out.noRotation = !+out.shear.toFixed(9) && !out.rotate, out
        }, matrixproto.toTransformString = function (shorter) {
            var s = shorter || this[split]();
            return s.isSimple ? (s.scalex = +s.scalex.toFixed(4), s.scaley = +s.scaley.toFixed(4), s.rotate = +s.rotate.toFixed(4), (s.dx || s.dy ? "t" + [s.dx, s.dy] : E) + (1 != s.scalex || 1 != s.scaley ? "s" + [s.scalex, s.scaley, 0, 0] : E) + (s.rotate ? "r" + [s.rotate, 0, 0] : E)) : "m" + [this.get(0), this.get(1), this.get(2), this.get(3), this.get(4), this.get(5)]
        }
    }(Matrix.prototype);
    var version = navigator.userAgent.match(/Version\/(.*?)\s/) || navigator.userAgent.match(/Chrome\/(\d+)/);
    paperproto.safari = "Apple Computer, Inc." == navigator.vendor && (version && version[1] < 4 || "iP" == navigator.platform.slice(0, 2)) || "Google Inc." == navigator.vendor && version && version[1] < 8 ? function () {
        var rect = this.rect(-99, -99, this.width + 99, this.height + 99).attr({stroke: "none"});
        setTimeout(function () {
            rect.remove()
        })
    } : fun;
    for (var preventDefault = function () {
        this.returnValue = !1
    }, preventTouch = function () {
        return this.originalEvent.preventDefault()
    }, stopPropagation = function () {
        this.cancelBubble = !0
    }, stopTouch = function () {
        return this.originalEvent.stopPropagation()
    }, getEventPosition = function (e) {
        var scrollY = g.doc.documentElement.scrollTop || g.doc.body.scrollTop,
            scrollX = g.doc.documentElement.scrollLeft || g.doc.body.scrollLeft;
        return {x: e.clientX + scrollX, y: e.clientY + scrollY}
    }, addEvent = function () {
        return g.doc.addEventListener ? function (obj, type, fn, element) {
            var f = function (e) {
                var pos = getEventPosition(e);
                return fn.call(element, e, pos.x, pos.y)
            };
            if (obj.addEventListener(type, f, !1), supportsTouch && touchMap[type]) {
                var _f = function (e) {
                    for (var pos = getEventPosition(e), olde = e, i = 0, ii = e.targetTouches && e.targetTouches.length; ii > i; i++) if (e.targetTouches[i].target == obj) {
                        e = e.targetTouches[i], e.originalEvent = olde, e.preventDefault = preventTouch, e.stopPropagation = stopTouch;
                        break
                    }
                    return fn.call(element, e, pos.x, pos.y)
                };
                obj.addEventListener(touchMap[type], _f, !1)
            }
            return function () {
                return obj.removeEventListener(type, f, !1), supportsTouch && touchMap[type] && obj.removeEventListener(touchMap[type], f, !1), !0
            }
        } : g.doc.attachEvent ? function (obj, type, fn, element) {
            var f = function (e) {
                e = e || g.win.event;
                var scrollY = g.doc.documentElement.scrollTop || g.doc.body.scrollTop,
                    scrollX = g.doc.documentElement.scrollLeft || g.doc.body.scrollLeft, x = e.clientX + scrollX,
                    y = e.clientY + scrollY;
                return e.preventDefault = e.preventDefault || preventDefault, e.stopPropagation = e.stopPropagation || stopPropagation, fn.call(element, e, x, y)
            };
            obj.attachEvent("on" + type, f);
            var detacher = function () {
                return obj.detachEvent("on" + type, f), !0
            };
            return detacher
        } : void 0
    }(), drag = [], dragMove = function (e) {
        for (var dragi, x = e.clientX, y = e.clientY, scrollY = g.doc.documentElement.scrollTop || g.doc.body.scrollTop, scrollX = g.doc.documentElement.scrollLeft || g.doc.body.scrollLeft, j = drag.length; j--;) {
            if (dragi = drag[j], supportsTouch && e.touches) {
                for (var touch, i = e.touches.length; i--;) if (touch = e.touches[i], touch.identifier == dragi.el._drag.id) {
                    x = touch.clientX, y = touch.clientY, (e.originalEvent ? e.originalEvent : e).preventDefault();
                    break
                }
            } else e.preventDefault();
            var o, node = dragi.el.node, next = node.nextSibling, parent = node.parentNode,
                display = node.style.display;
            g.win.opera && parent.removeChild(node), node.style.display = "none", o = dragi.el.paper.getElementByPoint(x, y), node.style.display = display, g.win.opera && (next ? parent.insertBefore(node, next) : parent.appendChild(node)), o && eve("raphael.drag.over." + dragi.el.id, dragi.el, o), x += scrollX, y += scrollY, eve("raphael.drag.move." + dragi.el.id, dragi.move_scope || dragi.el, x - dragi.el._drag.x, y - dragi.el._drag.y, x, y, e)
        }
    }, dragUp = function (e) {
        R.unmousemove(dragMove).unmouseup(dragUp);
        for (var dragi, i = drag.length; i--;) dragi = drag[i], dragi.el._drag = {}, eve("raphael.drag.end." + dragi.el.id, dragi.end_scope || dragi.start_scope || dragi.move_scope || dragi.el, e);
        drag = []
    }, elproto = R.el = {}, i = events.length; i--;) !function (eventName) {
        R[eventName] = elproto[eventName] = function (fn, scope) {
            return R.is(fn, "function") && (this.events = this.events || [], this.events.push({
                name: eventName,
                f: fn,
                unbind: addEvent(this.shape || this.node || g.doc, eventName, fn, scope || this)
            })), this
        }, R["un" + eventName] = elproto["un" + eventName] = function (fn) {
            for (var events = this.events || [], l = events.length; l--;) events[l].name != eventName || !R.is(fn, "undefined") && events[l].f != fn || (events[l].unbind(), events.splice(l, 1), !events.length && delete this.events);
            return this
        }
    }(events[i]);
    elproto.data = function (key, value) {
        var data = eldata[this.id] = eldata[this.id] || {};
        if (0 == arguments.length) return data;
        if (1 == arguments.length) {
            if (R.is(key, "object")) {
                for (var i in key) key[has](i) && this.data(i, key[i]);
                return this
            }
            return eve("raphael.data.get." + this.id, this, data[key], key), data[key]
        }
        return data[key] = value, eve("raphael.data.set." + this.id, this, value, key), this
    }, elproto.removeData = function (key) {
        return null == key ? eldata[this.id] = {} : eldata[this.id] && delete eldata[this.id][key], this
    }, elproto.getData = function () {
        return clone(eldata[this.id] || {})
    }, elproto.hover = function (f_in, f_out, scope_in, scope_out) {
        return this.mouseover(f_in, scope_in).mouseout(f_out, scope_out || scope_in)
    }, elproto.unhover = function (f_in, f_out) {
        return this.unmouseover(f_in).unmouseout(f_out)
    };
    var draggable = [];
    elproto.drag = function (onmove, onstart, onend, move_scope, start_scope, end_scope) {
        function start(e) {
            (e.originalEvent || e).preventDefault();
            var x = e.clientX, y = e.clientY, scrollY = g.doc.documentElement.scrollTop || g.doc.body.scrollTop,
                scrollX = g.doc.documentElement.scrollLeft || g.doc.body.scrollLeft;
            if (this._drag.id = e.identifier, supportsTouch && e.touches) for (var touch, i = e.touches.length; i--;) if (touch = e.touches[i], this._drag.id = touch.identifier, touch.identifier == this._drag.id) {
                x = touch.clientX, y = touch.clientY;
                break
            }
            this._drag.x = x + scrollX, this._drag.y = y + scrollY, !drag.length && R.mousemove(dragMove).mouseup(dragUp), drag.push({
                el: this,
                move_scope: move_scope,
                start_scope: start_scope,
                end_scope: end_scope
            }), onstart && eve.on("raphael.drag.start." + this.id, onstart), onmove && eve.on("raphael.drag.move." + this.id, onmove), onend && eve.on("raphael.drag.end." + this.id, onend), eve("raphael.drag.start." + this.id, start_scope || move_scope || this, e.clientX + scrollX, e.clientY + scrollY, e)
        }

        return this._drag = {}, draggable.push({el: this, start: start}), this.mousedown(start), this
    }, elproto.onDragOver = function (f) {
        f ? eve.on("raphael.drag.over." + this.id, f) : eve.unbind("raphael.drag.over." + this.id)
    }, elproto.undrag = function () {
        for (var i = draggable.length; i--;) draggable[i].el == this && (this.unmousedown(draggable[i].start), draggable.splice(i, 1), eve.unbind("raphael.drag.*." + this.id));
        !draggable.length && R.unmousemove(dragMove).unmouseup(dragUp), drag = []
    }, paperproto.circle = function (x, y, r) {
        var out = R._engine.circle(this, x || 0, y || 0, r || 0);
        return this.__set__ && this.__set__.push(out), out
    }, paperproto.rect = function (x, y, w, h, r) {
        var out = R._engine.rect(this, x || 0, y || 0, w || 0, h || 0, r || 0);
        return this.__set__ && this.__set__.push(out), out
    }, paperproto.ellipse = function (x, y, rx, ry) {
        var out = R._engine.ellipse(this, x || 0, y || 0, rx || 0, ry || 0);
        return this.__set__ && this.__set__.push(out), out
    }, paperproto.path = function (pathString) {
        pathString && !R.is(pathString, string) && !R.is(pathString[0], array) && (pathString += E);
        var out = R._engine.path(R.format[apply](R, arguments), this);
        return this.__set__ && this.__set__.push(out), out
    }, paperproto.image = function (src, x, y, w, h) {
        var out = R._engine.image(this, src || "about:blank", x || 0, y || 0, w || 0, h || 0);
        return this.__set__ && this.__set__.push(out), out
    }, paperproto.text = function (x, y, text) {
        var out = R._engine.text(this, x || 0, y || 0, Str(text));
        return this.__set__ && this.__set__.push(out), out
    }, paperproto.set = function (itemsArray) {
        !R.is(itemsArray, "array") && (itemsArray = Array.prototype.splice.call(arguments, 0, arguments.length));
        var out = new Set(itemsArray);
        return this.__set__ && this.__set__.push(out), out.paper = this, out.type = "set", out
    }, paperproto.setStart = function (set) {
        this.__set__ = set || this.set()
    }, paperproto.setFinish = function () {
        var out = this.__set__;
        return delete this.__set__, out
    }, paperproto.setSize = function (width, height) {
        return R._engine.setSize.call(this, width, height)
    }, paperproto.setViewBox = function (x, y, w, h, fit) {
        return R._engine.setViewBox.call(this, x, y, w, h, fit)
    }, paperproto.top = paperproto.bottom = null, paperproto.raphael = R;
    var getOffset = function (elem) {
        var box = elem.getBoundingClientRect(), doc = elem.ownerDocument, body = doc.body,
            docElem = doc.documentElement, clientTop = docElem.clientTop || body.clientTop || 0,
            clientLeft = docElem.clientLeft || body.clientLeft || 0,
            top = box.top + (g.win.pageYOffset || docElem.scrollTop || body.scrollTop) - clientTop,
            left = box.left + (g.win.pageXOffset || docElem.scrollLeft || body.scrollLeft) - clientLeft;
        return {y: top, x: left}
    };
    paperproto.getElementByPoint = function (x, y) {
        var paper = this, svg = paper.canvas, target = g.doc.elementFromPoint(x, y);
        if (g.win.opera && "svg" == target.tagName) {
            var so = getOffset(svg), sr = svg.createSVGRect();
            sr.x = x - so.x, sr.y = y - so.y, sr.width = sr.height = 1;
            var hits = svg.getIntersectionList(sr, null);
            hits.length && (target = hits[hits.length - 1])
        }
        if (!target) return null;
        for (; target.parentNode && target != svg.parentNode && !target.raphael;) target = target.parentNode;
        return target == paper.canvas.parentNode && (target = svg), target = target && target.raphael ? paper.getById(target.raphaelid) : null
    }, paperproto.getElementsByBBox = function (bbox) {
        var set = this.set();
        return this.forEach(function (el) {
            R.isBBoxIntersect(el.getBBox(), bbox) && set.push(el)
        }), set
    }, paperproto.getById = function (id) {
        for (var bot = this.bottom; bot;) {
            if (bot.id == id) return bot;
            bot = bot.next
        }
        return null
    }, paperproto.forEach = function (callback, thisArg) {
        for (var bot = this.bottom; bot;) {
            if (callback.call(thisArg, bot) === !1) return this;
            bot = bot.next
        }
        return this
    }, paperproto.getElementsByPoint = function (x, y) {
        var set = this.set();
        return this.forEach(function (el) {
            el.isPointInside(x, y) && set.push(el)
        }), set
    }, elproto.isPointInside = function (x, y) {
        var rp = this.realPath = getPath[this.type](this);
        return this.attr("transform") && this.attr("transform").length && (rp = R.transformPath(rp, this.attr("transform"))), R.isPointInsidePath(rp, x, y)
    }, elproto.getBBox = function (isWithoutTransform) {
        if (this.removed) return {};
        var _ = this._;
        return isWithoutTransform ? ((_.dirty || !_.bboxwt) && (this.realPath = getPath[this.type](this), _.bboxwt = pathDimensions(this.realPath), _.bboxwt.toString = x_y_w_h, _.dirty = 0), _.bboxwt) : ((_.dirty || _.dirtyT || !_.bbox) && ((_.dirty || !this.realPath) && (_.bboxwt = 0, this.realPath = getPath[this.type](this)), _.bbox = pathDimensions(mapPath(this.realPath, this.matrix)), _.bbox.toString = x_y_w_h, _.dirty = _.dirtyT = 0), _.bbox)
    }, elproto.clone = function () {
        if (this.removed) return null;
        var out = this.paper[this.type]().attr(this.attr());
        return this.__set__ && this.__set__.push(out), out
    }, elproto.glow = function (glow) {
        if ("text" == this.type) return null;
        glow = glow || {};
        var s = {
            width: (glow.width || 10) + (+this.attr("stroke-width") || 1),
            fill: glow.fill || !1,
            opacity: glow.opacity || .5,
            offsetx: glow.offsetx || 0,
            offsety: glow.offsety || 0,
            color: glow.color || "#000"
        }, c = s.width / 2, r = this.paper, out = r.set(), path = this.realPath || getPath[this.type](this);
        path = this.matrix ? mapPath(path, this.matrix) : path;
        for (var i = 1; c + 1 > i; i++) out.push(r.path(path).attr({
            stroke: s.color,
            fill: s.fill ? s.color : "none",
            "stroke-linejoin": "round",
            "stroke-linecap": "round",
            "stroke-width": +(s.width / c * i).toFixed(3),
            opacity: +(s.opacity / c).toFixed(3)
        }));
        return out.insertBefore(this).translate(s.offsetx, s.offsety)
    };
    var getPointAtSegmentLength = function (p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, length) {
            return null == length ? bezlen(p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y) : R.findDotsAtSegment(p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, getTatLen(p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y, length))
        }, getLengthFactory = function (istotal, subpath) {
            return function (path, length, onlystart) {
                path = path2curve(path);
                for (var x, y, p, l, point, sp = "", subpaths = {}, len = 0, i = 0, ii = path.length; ii > i; i++) {
                    if (p = path[i], "M" == p[0]) x = +p[1], y = +p[2]; else {
                        if (l = getPointAtSegmentLength(x, y, p[1], p[2], p[3], p[4], p[5], p[6]), len + l > length) {
                            if (subpath && !subpaths.start) {
                                if (point = getPointAtSegmentLength(x, y, p[1], p[2], p[3], p[4], p[5], p[6], length - len), sp += ["C" + point.start.x, point.start.y, point.m.x, point.m.y, point.x, point.y], onlystart) return sp;
                                subpaths.start = sp, sp = ["M" + point.x, point.y + "C" + point.n.x, point.n.y, point.end.x, point.end.y, p[5], p[6]].join(), len += l, x = +p[5], y = +p[6];
                                continue
                            }
                            if (!istotal && !subpath) return point = getPointAtSegmentLength(x, y, p[1], p[2], p[3], p[4], p[5], p[6], length - len), {
                                x: point.x,
                                y: point.y,
                                alpha: point.alpha
                            }
                        }
                        len += l, x = +p[5], y = +p[6]
                    }
                    sp += p.shift() + p
                }
                return subpaths.end = sp, point = istotal ? len : subpath ? subpaths : R.findDotsAtSegment(x, y, p[0], p[1], p[2], p[3], p[4], p[5], 1), point.alpha && (point = {
                    x: point.x,
                    y: point.y,
                    alpha: point.alpha
                }), point
            }
        }, getTotalLength = getLengthFactory(1), getPointAtLength = getLengthFactory(),
        getSubpathsAtLength = getLengthFactory(0, 1);
    R.getTotalLength = getTotalLength, R.getPointAtLength = getPointAtLength, R.getSubpath = function (path, from, to) {
        if (this.getTotalLength(path) - to < 1e-6) return getSubpathsAtLength(path, from).end;
        var a = getSubpathsAtLength(path, to, 1);
        return from ? getSubpathsAtLength(a, from).end : a
    }, elproto.getTotalLength = function () {
        var path = this.getPath();
        if (path) return this.node.getTotalLength ? this.node.getTotalLength() : getTotalLength(path)
    }, elproto.getPointAtLength = function (length) {
        var path = this.getPath();
        if (path) return getPointAtLength(path, length)
    }, elproto.getPath = function () {
        var path, getPath = R._getPath[this.type];
        if ("text" != this.type && "set" != this.type) return getPath && (path = getPath(this)), path
    }, elproto.getSubpath = function (from, to) {
        var path = this.getPath();
        if (path) return R.getSubpath(path, from, to)
    };
    var ef = R.easing_formulas = {
        linear: function (n) {
            return n
        }, "<": function (n) {
            return pow(n, 1.7)
        }, ">": function (n) {
            return pow(n, .48)
        }, "<>": function (n) {
            var q = .48 - n / 1.04, Q = math.sqrt(.1734 + q * q), x = Q - q, X = pow(abs(x), 1 / 3) * (0 > x ? -1 : 1),
                y = -Q - q, Y = pow(abs(y), 1 / 3) * (0 > y ? -1 : 1), t = X + Y + .5;
            return 3 * (1 - t) * t * t + t * t * t
        }, backIn: function (n) {
            var s = 1.70158;
            return n * n * ((s + 1) * n - s)
        }, backOut: function (n) {
            n -= 1;
            var s = 1.70158;
            return n * n * ((s + 1) * n + s) + 1
        }, elastic: function (n) {
            return n == !!n ? n : pow(2, -10 * n) * math.sin(2 * (n - .075) * PI / .3) + 1
        }, bounce: function (n) {
            var l, s = 7.5625, p = 2.75;
            return 1 / p > n ? l = s * n * n : 2 / p > n ? (n -= 1.5 / p, l = s * n * n + .75) : 2.5 / p > n ? (n -= 2.25 / p, l = s * n * n + .9375) : (n -= 2.625 / p, l = s * n * n + .984375), l
        }
    };
    ef.easeIn = ef["ease-in"] = ef["<"], ef.easeOut = ef["ease-out"] = ef[">"], ef.easeInOut = ef["ease-in-out"] = ef["<>"], ef["back-in"] = ef.backIn, ef["back-out"] = ef.backOut;
    var animationElements = [],
        requestAnimFrame = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (callback) {
            setTimeout(callback, 16)
        }, animation = function () {
            for (var Now = +new Date, l = 0; l < animationElements.length; l++) {
                var e = animationElements[l];
                if (!e.el.removed && !e.paused) {
                    var now, key, time = Now - e.start, ms = e.ms, easing = e.easing, from = e.from, diff = e.diff,
                        to = e.to, that = (e.t, e.el), set = {}, init = {};
                    if (e.initstatus ? (time = (e.initstatus * e.anim.top - e.prev) / (e.percent - e.prev) * ms, e.status = e.initstatus, delete e.initstatus, e.stop && animationElements.splice(l--, 1)) : e.status = (e.prev + (e.percent - e.prev) * (time / ms)) / e.anim.top, !(0 > time)) if (ms > time) {
                        var pos = easing(time / ms);
                        for (var attr in from) if (from[has](attr)) {
                            switch (availableAnimAttrs[attr]) {
                                case nu:
                                    now = +from[attr] + pos * ms * diff[attr];
                                    break;
                                case"colour":
                                    now = "rgb(" + [upto255(round(from[attr].r + pos * ms * diff[attr].r)), upto255(round(from[attr].g + pos * ms * diff[attr].g)), upto255(round(from[attr].b + pos * ms * diff[attr].b))].join(",") + ")";
                                    break;
                                case"path":
                                    now = [];
                                    for (var i = 0, ii = from[attr].length; ii > i; i++) {
                                        now[i] = [from[attr][i][0]];
                                        for (var j = 1, jj = from[attr][i].length; jj > j; j++) now[i][j] = +from[attr][i][j] + pos * ms * diff[attr][i][j];
                                        now[i] = now[i].join(S)
                                    }
                                    now = now.join(S);
                                    break;
                                case"transform":
                                    if (diff[attr].real) for (now = [], i = 0, ii = from[attr].length; ii > i; i++) for (now[i] = [from[attr][i][0]], j = 1, jj = from[attr][i].length; jj > j; j++) now[i][j] = from[attr][i][j] + pos * ms * diff[attr][i][j]; else {
                                        var get = function (i) {
                                            return +from[attr][i] + pos * ms * diff[attr][i]
                                        };
                                        now = [["m", get(0), get(1), get(2), get(3), get(4), get(5)]]
                                    }
                                    break;
                                case"csv":
                                    if ("clip-rect" == attr) for (now = [], i = 4; i--;) now[i] = +from[attr][i] + pos * ms * diff[attr][i];
                                    break;
                                default:
                                    var from2 = [][concat](from[attr]);
                                    for (now = [], i = that.paper.customAttributes[attr].length; i--;) now[i] = +from2[i] + pos * ms * diff[attr][i]
                            }
                            set[attr] = now
                        }
                        that.attr(set), function (id, that, anim) {
                            setTimeout(function () {
                                eve("raphael.anim.frame." + id, that, anim)
                            })
                        }(that.id, that, e.anim)
                    } else {
                        if (function (f, el, a) {
                            setTimeout(function () {
                                eve("raphael.anim.frame." + el.id, el, a), eve("raphael.anim.finish." + el.id, el, a), R.is(f, "function") && f.call(el)
                            })
                        }(e.callback, that, e.anim), that.attr(to), animationElements.splice(l--, 1), e.repeat > 1 && !e.next) {
                            for (key in to) to[has](key) && (init[key] = e.totalOrigin[key]);
                            e.el.attr(init), runAnimation(e.anim, e.el, e.anim.percents[0], null, e.totalOrigin, e.repeat - 1)
                        }
                        e.next && !e.stop && runAnimation(e.anim, e.el, e.next, null, e.totalOrigin, e.repeat)
                    }
                }
            }
            R.svg && that && that.paper && that.paper.safari(), animationElements.length && requestAnimFrame(animation)
        }, upto255 = function (color) {
            return color > 255 ? 255 : 0 > color ? 0 : color
        };
    elproto.animateWith = function (el, anim, params, ms, easing, callback) {
        var element = this;
        if (element.removed) return callback && callback.call(element), element;
        var a = params instanceof Animation ? params : R.animation(params, ms, easing, callback);
        runAnimation(a, element, a.percents[0], null, element.attr());
        for (var i = 0, ii = animationElements.length; ii > i; i++) if (animationElements[i].anim == anim && animationElements[i].el == el) {
            animationElements[ii - 1].start = animationElements[i].start;
            break
        }
        return element
    }, elproto.onAnimation = function (f) {
        return f ? eve.on("raphael.anim.frame." + this.id, f) : eve.unbind("raphael.anim.frame." + this.id), this
    }, Animation.prototype.delay = function (delay) {
        var a = new Animation(this.anim, this.ms);
        return a.times = this.times, a.del = +delay || 0, a
    }, Animation.prototype.repeat = function (times) {
        var a = new Animation(this.anim, this.ms);
        return a.del = this.del, a.times = math.floor(mmax(times, 0)) || 1, a
    }, R.animation = function (params, ms, easing, callback) {
        if (params instanceof Animation) return params;
        (R.is(easing, "function") || !easing) && (callback = callback || easing || null, easing = null), params = Object(params), ms = +ms || 0;
        var json, attr, p = {};
        for (attr in params) params[has](attr) && toFloat(attr) != attr && toFloat(attr) + "%" != attr && (json = !0, p[attr] = params[attr]);
        return json ? (easing && (p.easing = easing), callback && (p.callback = callback), new Animation({100: p}, ms)) : new Animation(params, ms)
    }, elproto.animate = function (params, ms, easing, callback) {
        var element = this;
        if (element.removed) return callback && callback.call(element), element;
        var anim = params instanceof Animation ? params : R.animation(params, ms, easing, callback);
        return runAnimation(anim, element, anim.percents[0], null, element.attr()), element
    }, elproto.setTime = function (anim, value) {
        return anim && null != value && this.status(anim, mmin(value, anim.ms) / anim.ms), this
    }, elproto.status = function (anim, value) {
        var len, e, out = [], i = 0;
        if (null != value) return runAnimation(anim, this, -1, mmin(value, 1)), this;
        for (len = animationElements.length; len > i; i++) if (e = animationElements[i], e.el.id == this.id && (!anim || e.anim == anim)) {
            if (anim) return e.status;
            out.push({anim: e.anim, status: e.status})
        }
        return anim ? 0 : out
    }, elproto.pause = function (anim) {
        for (var i = 0; i < animationElements.length; i++) animationElements[i].el.id != this.id || anim && animationElements[i].anim != anim || eve("raphael.anim.pause." + this.id, this, animationElements[i].anim) !== !1 && (animationElements[i].paused = !0);
        return this
    }, elproto.resume = function (anim) {
        for (var i = 0; i < animationElements.length; i++) if (animationElements[i].el.id == this.id && (!anim || animationElements[i].anim == anim)) {
            var e = animationElements[i];
            eve("raphael.anim.resume." + this.id, this, e.anim) !== !1 && (delete e.paused, this.status(e.anim, e.status))
        }
        return this
    }, elproto.stop = function (anim) {
        for (var i = 0; i < animationElements.length; i++) animationElements[i].el.id != this.id || anim && animationElements[i].anim != anim || eve("raphael.anim.stop." + this.id, this, animationElements[i].anim) !== !1 && animationElements.splice(i--, 1);
        return this
    }, eve.on("raphael.remove", stopAnimation), eve.on("raphael.clear", stopAnimation), elproto.toString = function () {
        return "Rapha毛l鈥檚 object"
    };
    var Set = function (items) {
        if (this.items = [], this.length = 0, this.type = "set", items) for (var i = 0, ii = items.length; ii > i; i++) !items[i] || items[i].constructor != elproto.constructor && items[i].constructor != Set || (this[this.items.length] = this.items[this.items.length] = items[i], this.length++)
    }, setproto = Set.prototype;
    setproto.push = function () {
        for (var item, len, i = 0, ii = arguments.length; ii > i; i++) item = arguments[i], !item || item.constructor != elproto.constructor && item.constructor != Set || (len = this.items.length, this[len] = this.items[len] = item, this.length++);
        return this
    }, setproto.pop = function () {
        return this.length && delete this[this.length--], this.items.pop()
    }, setproto.forEach = function (callback, thisArg) {
        for (var i = 0, ii = this.items.length; ii > i; i++) if (callback.call(thisArg, this.items[i], i) === !1) return this;
        return this
    };
    for (var method in elproto) elproto[has](method) && (setproto[method] = function (methodname) {
        return function () {
            var arg = arguments;
            return this.forEach(function (el) {
                el[methodname][apply](el, arg)
            })
        }
    }(method));
    return setproto.attr = function (name, value) {
        if (name && R.is(name, array) && R.is(name[0], "object")) for (var j = 0, jj = name.length; jj > j; j++) this.items[j].attr(name[j]); else for (var i = 0, ii = this.items.length; ii > i; i++) this.items[i].attr(name, value);
        return this
    }, setproto.clear = function () {
        for (; this.length;) this.pop()
    }, setproto.splice = function (index, count) {
        index = 0 > index ? mmax(this.length + index, 0) : index, count = mmax(0, mmin(this.length - index, count));
        var i, tail = [], todel = [], args = [];
        for (i = 2; i < arguments.length; i++) args.push(arguments[i]);
        for (i = 0; count > i; i++) todel.push(this[index + i]);
        for (; i < this.length - index; i++) tail.push(this[index + i]);
        var arglen = args.length;
        for (i = 0; i < arglen + tail.length; i++) this.items[index + i] = this[index + i] = arglen > i ? args[i] : tail[i - arglen];
        for (i = this.items.length = this.length -= count - arglen; this[i];) delete this[i++];
        return new Set(todel)
    }, setproto.exclude = function (el) {
        for (var i = 0, ii = this.length; ii > i; i++) if (this[i] == el) return this.splice(i, 1), !0
    }, setproto.animate = function (params, ms, easing, callback) {
        (R.is(easing, "function") || !easing) && (callback = easing || null);
        var item, collector, len = this.items.length, i = len, set = this;
        if (!len) return this;
        callback && (collector = function () {
            !--len && callback.call(set)
        }), easing = R.is(easing, string) ? easing : collector;
        var anim = R.animation(params, ms, easing, collector);
        for (item = this.items[--i].animate(anim); i--;) this.items[i] && !this.items[i].removed && this.items[i].animateWith(item, anim, anim), this.items[i] && !this.items[i].removed || len--;
        return this
    }, setproto.insertAfter = function (el) {
        for (var i = this.items.length; i--;) this.items[i].insertAfter(el);
        return this
    }, setproto.getBBox = function () {
        for (var x = [], y = [], x2 = [], y2 = [], i = this.items.length; i--;) if (!this.items[i].removed) {
            var box = this.items[i].getBBox();
            x.push(box.x), y.push(box.y), x2.push(box.x + box.width), y2.push(box.y + box.height)
        }
        return x = mmin[apply](0, x), y = mmin[apply](0, y), x2 = mmax[apply](0, x2), y2 = mmax[apply](0, y2), {
            x: x,
            y: y,
            x2: x2,
            y2: y2,
            width: x2 - x,
            height: y2 - y
        }
    }, setproto.clone = function (s) {
        s = this.paper.set();
        for (var i = 0, ii = this.items.length; ii > i; i++) s.push(this.items[i].clone());
        return s
    }, setproto.toString = function () {
        return "Rapha毛l鈥榮 set"
    }, setproto.glow = function (glowConfig) {
        var ret = this.paper.set();
        return this.forEach(function (shape) {
            var g = shape.glow(glowConfig);
            null != g && g.forEach(function (shape2) {
                ret.push(shape2)
            })
        }), ret
    }, setproto.isPointInside = function (x, y) {
        var isPointInside = !1;
        return this.forEach(function (el) {
            return el.isPointInside(x, y) ? (console.log("runned"), isPointInside = !0, !1) : void 0
        }), isPointInside
    }, R.registerFont = function (font) {
        if (!font.face) return font;
        this.fonts = this.fonts || {};
        var fontcopy = {w: font.w, face: {}, glyphs: {}}, family = font.face["font-family"];
        for (var prop in font.face) font.face[has](prop) && (fontcopy.face[prop] = font.face[prop]);
        if (this.fonts[family] ? this.fonts[family].push(fontcopy) : this.fonts[family] = [fontcopy], !font.svg) {
            fontcopy.face["units-per-em"] = toInt(font.face["units-per-em"], 10);
            for (var glyph in font.glyphs) if (font.glyphs[has](glyph)) {
                var path = font.glyphs[glyph];
                if (fontcopy.glyphs[glyph] = {
                    w: path.w,
                    k: {},
                    d: path.d && "M" + path.d.replace(/[mlcxtrv]/g, function (command) {
                        return {l: "L", c: "C", x: "z", t: "m", r: "l", v: "c"}[command] || "M"
                    }) + "z"
                }, path.k) for (var k in path.k) path[has](k) && (fontcopy.glyphs[glyph].k[k] = path.k[k])
            }
        }
        return font
    }, paperproto.getFont = function (family, weight, style, stretch) {
        if (stretch = stretch || "normal", style = style || "normal", weight = +weight || {
            normal: 400,
            bold: 700,
            lighter: 300,
            bolder: 800
        }[weight] || 400, R.fonts) {
            var font = R.fonts[family];
            if (!font) {
                var name = new RegExp("(^|\\s)" + family.replace(/[^\w\d\s+!~.:_-]/g, E) + "(\\s|$)", "i");
                for (var fontName in R.fonts) if (R.fonts[has](fontName) && name.test(fontName)) {
                    font = R.fonts[fontName];
                    break
                }
            }
            var thefont;
            if (font) for (var i = 0, ii = font.length; ii > i && (thefont = font[i], thefont.face["font-weight"] != weight || thefont.face["font-style"] != style && thefont.face["font-style"] || thefont.face["font-stretch"] != stretch); i++) ;
            return thefont
        }
    }, paperproto.print = function (x, y, string, font, size, origin, letter_spacing, line_spacing) {
        origin = origin || "middle", letter_spacing = mmax(mmin(letter_spacing || 0, 1), -1), line_spacing = mmax(mmin(line_spacing || 1, 3), 1);
        var scale, letters = Str(string)[split](E), shift = 0, notfirst = 0, path = E;
        if (R.is(font, "string") && (font = this.getFont(font)), font) {
            scale = (size || 16) / font.face["units-per-em"];
            for (var bb = font.face.bbox[split](separator), top = +bb[0], lineHeight = bb[3] - bb[1], shifty = 0, height = +bb[1] + ("baseline" == origin ? lineHeight + +font.face.descent : lineHeight / 2), i = 0, ii = letters.length; ii > i; i++) {
                if ("\n" == letters[i]) shift = 0, curr = 0, notfirst = 0, shifty += lineHeight * line_spacing; else {
                    var prev = notfirst && font.glyphs[letters[i - 1]] || {}, curr = font.glyphs[letters[i]];
                    shift += notfirst ? (prev.w || font.w) + (prev.k && prev.k[letters[i]] || 0) + font.w * letter_spacing : 0, notfirst = 1
                }
                curr && curr.d && (path += R.transformPath(curr.d, ["t", shift * scale, shifty * scale, "s", scale, scale, top, height, "t", (x - top) / scale, (y - height) / scale]))
            }
        }
        return this.path(path).attr({fill: "#000", stroke: "none"})
    }, paperproto.add = function (json) {
        if (R.is(json, "array")) for (var j, res = this.set(), i = 0, ii = json.length; ii > i; i++) j = json[i] || {}, elements[has](j.type) && res.push(this[j.type]().attr(j));
        return res
    }, R.format = function (token, params) {
        var args = R.is(params, array) ? [0][concat](params) : arguments;
        return token && R.is(token, string) && args.length - 1 && (token = token.replace(formatrg, function (str, i) {
            return null == args[++i] ? E : args[i]
        })), token || E
    }, R.fullfill = function () {
        var tokenRegex = /\{([^\}]+)\}/g, objNotationRegex = /(?:(?:^|\.)(.+?)(?=\[|\.|$|\()|\[('|")(.+?)\2\])(\(\))?/g,
            replacer = function (all, key, obj) {
                var res = obj;
                return key.replace(objNotationRegex, function (all, name, quote, quotedName, isFunc) {
                    name = name || quotedName, res && (name in res && (res = res[name]), "function" == typeof res && isFunc && (res = res()))
                }), res = (null == res || res == obj ? all : res) + ""
            };
        return function (str, obj) {
            return String(str).replace(tokenRegex, function (all, key) {
                return replacer(all, key, obj)
            })
        }
    }(), R.ninja = function () {
        return oldRaphael.was ? g.win.Raphael = oldRaphael.is : delete Raphael, R
    }, R.st = setproto, function (doc, loaded, f) {
        function isLoaded() {
            /in/.test(doc.readyState) ? setTimeout(isLoaded, 9) : R.eve("raphael.DOMload")
        }

        null == doc.readyState && doc.addEventListener && (doc.addEventListener(loaded, f = function () {
            doc.removeEventListener(loaded, f, !1), doc.readyState = "complete"
        }, !1), doc.readyState = "loading"), isLoaded()
    }(document, "DOMContentLoaded"), eve.on("raphael.DOMload", function () {
        loaded = !0
    }), function () {
        if (R.svg) {
            var has = "hasOwnProperty", Str = String, toFloat = parseFloat, toInt = parseInt, math = Math,
                mmax = math.max, abs = math.abs, pow = math.pow, separator = /[, ]+/, eve = R.eve, E = "", S = " ",
                xlink = "http://www.w3.org/1999/xlink", markers = {
                    block: "M5,0 0,2.5 5,5z",
                    classic: "M5,0 0,2.5 5,5 3.5,3 3.5,2z",
                    diamond: "M2.5,0 5,2.5 2.5,5 0,2.5z",
                    open: "M6,1 1,3.5 6,6",
                    oval: "M2.5,0A2.5,2.5,0,0,1,2.5,5 2.5,2.5,0,0,1,2.5,0z"
                }, markerCounter = {};
            R.toString = function () {
                return "Your browser supports SVG.\nYou are running Rapha毛l " + this.version
            };
            var $ = function (el, attr) {
                if (attr) {
                    "string" == typeof el && (el = $(el));
                    for (var key in attr) attr[has](key) && ("xlink:" == key.substring(0, 6) ? el.setAttributeNS(xlink, key.substring(6), Str(attr[key])) : el.setAttribute(key, Str(attr[key])))
                } else el = R._g.doc.createElementNS("http://www.w3.org/2000/svg", el), el.style && (el.style.webkitTapHighlightColor = "rgba(0,0,0,0)");
                return el
            }, addGradientFill = function (element, gradient) {
                var type = "linear", id = element.id + gradient, fx = .5, fy = .5, o = element.node,
                    SVG = element.paper, s = o.style, el = R._g.doc.getElementById(id);
                if (!el) {
                    if (gradient = Str(gradient).replace(R._radial_gradient, function (all, _fx, _fy) {
                        if (type = "radial", _fx && _fy) {
                            fx = toFloat(_fx), fy = toFloat(_fy);
                            var dir = 2 * (fy > .5) - 1;
                            pow(fx - .5, 2) + pow(fy - .5, 2) > .25 && (fy = math.sqrt(.25 - pow(fx - .5, 2)) * dir + .5) && .5 != fy && (fy = fy.toFixed(5) - 1e-5 * dir)
                        }
                        return E
                    }), gradient = gradient.split(/\s*\-\s*/), "linear" == type) {
                        var angle = gradient.shift();
                        if (angle = -toFloat(angle), isNaN(angle)) return null;
                        var vector = [0, 0, math.cos(R.rad(angle)), math.sin(R.rad(angle))],
                            max = 1 / (mmax(abs(vector[2]), abs(vector[3])) || 1);
                        vector[2] *= max, vector[3] *= max, vector[2] < 0 && (vector[0] = -vector[2], vector[2] = 0), vector[3] < 0 && (vector[1] = -vector[3], vector[3] = 0)
                    }
                    var dots = R._parseDots(gradient);
                    if (!dots) return null;
                    if (id = id.replace(/[\(\)\s,\xb0#]/g, "_"), element.gradient && id != element.gradient.id && (SVG.defs.removeChild(element.gradient), delete element.gradient), !element.gradient) {
                        el = $(type + "Gradient", {id: id}), element.gradient = el, $(el, "radial" == type ? {
                            fx: fx,
                            fy: fy
                        } : {
                            x1: vector[0],
                            y1: vector[1],
                            x2: vector[2],
                            y2: vector[3],
                            gradientTransform: element.matrix.invert()
                        }), SVG.defs.appendChild(el);
                        for (var i = 0, ii = dots.length; ii > i; i++) el.appendChild($("stop", {
                            offset: dots[i].offset ? dots[i].offset : i ? "100%" : "0%",
                            "stop-color": dots[i].color || "#fff"
                        }))
                    }
                }
                return $(o, {
                    fill: "url(#" + id + ")",
                    opacity: 1,
                    "fill-opacity": 1
                }), s.fill = E, s.opacity = 1, s.fillOpacity = 1, 1
            }, updatePosition = function (o) {
                var bbox = o.getBBox(1);
                $(o.pattern, {patternTransform: o.matrix.invert() + " translate(" + bbox.x + "," + bbox.y + ")"})
            }, addArrow = function (o, value, isEnd) {
                if ("path" == o.type) {
                    for (var from, to, dx, refX, attr, values = Str(value).toLowerCase().split("-"), p = o.paper, se = isEnd ? "end" : "start", node = o.node, attrs = o.attrs, stroke = attrs["stroke-width"], i = values.length, type = "classic", w = 3, h = 3, t = 5; i--;) switch (values[i]) {
                        case"block":
                        case"classic":
                        case"oval":
                        case"diamond":
                        case"open":
                        case"none":
                            type = values[i];
                            break;
                        case"wide":
                            h = 5;
                            break;
                        case"narrow":
                            h = 2;
                            break;
                        case"long":
                            w = 5;
                            break;
                        case"short":
                            w = 2
                    }
                    if ("open" == type ? (w += 2, h += 2, t += 2, dx = 1, refX = isEnd ? 4 : 1, attr = {
                        fill: "none",
                        stroke: attrs.stroke
                    }) : (refX = dx = w / 2, attr = {
                        fill: attrs.stroke,
                        stroke: "none"
                    }), o._.arrows ? isEnd ? (o._.arrows.endPath && markerCounter[o._.arrows.endPath]--, o._.arrows.endMarker && markerCounter[o._.arrows.endMarker]--) : (o._.arrows.startPath && markerCounter[o._.arrows.startPath]--, o._.arrows.startMarker && markerCounter[o._.arrows.startMarker]--) : o._.arrows = {}, "none" != type) {
                        var pathId = "raphael-marker-" + type, markerId = "raphael-marker-" + se + type + w + h;
                        R._g.doc.getElementById(pathId) ? markerCounter[pathId]++ : (p.defs.appendChild($($("path"), {
                            "stroke-linecap": "round",
                            d: markers[type],
                            id: pathId
                        })), markerCounter[pathId] = 1);
                        var use, marker = R._g.doc.getElementById(markerId);
                        marker ? (markerCounter[markerId]++, use = marker.getElementsByTagName("use")[0]) : (marker = $($("marker"), {
                            id: markerId,
                            markerHeight: h,
                            markerWidth: w,
                            orient: "auto",
                            refX: refX,
                            refY: h / 2
                        }), use = $($("use"), {
                            "xlink:href": "#" + pathId,
                            transform: (isEnd ? "rotate(180 " + w / 2 + " " + h / 2 + ") " : E) + "scale(" + w / t + "," + h / t + ")",
                            "stroke-width": (1 / ((w / t + h / t) / 2)).toFixed(4)
                        }), marker.appendChild(use), p.defs.appendChild(marker), markerCounter[markerId] = 1), $(use, attr);
                        var delta = dx * ("diamond" != type && "oval" != type);
                        isEnd ? (from = o._.arrows.startdx * stroke || 0, to = R.getTotalLength(attrs.path) - delta * stroke) : (from = delta * stroke, to = R.getTotalLength(attrs.path) - (o._.arrows.enddx * stroke || 0)), attr = {}, attr["marker-" + se] = "url(#" + markerId + ")", (to || from) && (attr.d = R.getSubpath(attrs.path, from, to)), $(node, attr), o._.arrows[se + "Path"] = pathId, o._.arrows[se + "Marker"] = markerId, o._.arrows[se + "dx"] = delta, o._.arrows[se + "Type"] = type, o._.arrows[se + "String"] = value
                    } else isEnd ? (from = o._.arrows.startdx * stroke || 0, to = R.getTotalLength(attrs.path) - from) : (from = 0, to = R.getTotalLength(attrs.path) - (o._.arrows.enddx * stroke || 0)), o._.arrows[se + "Path"] && $(node, {d: R.getSubpath(attrs.path, from, to)}), delete o._.arrows[se + "Path"], delete o._.arrows[se + "Marker"], delete o._.arrows[se + "dx"], delete o._.arrows[se + "Type"], delete o._.arrows[se + "String"];
                    for (attr in markerCounter) if (markerCounter[has](attr) && !markerCounter[attr]) {
                        var item = R._g.doc.getElementById(attr);
                        item && item.parentNode.removeChild(item)
                    }
                }
            }, dasharray = {
                "": [0],
                none: [0],
                "-": [3, 1],
                ".": [1, 1],
                "-.": [3, 1, 1, 1],
                "-..": [3, 1, 1, 1, 1, 1],
                ". ": [1, 3],
                "- ": [4, 3],
                "--": [8, 3],
                "- .": [4, 3, 1, 3],
                "--.": [8, 3, 1, 3],
                "--..": [8, 3, 1, 3, 1, 3]
            }, addDashes = function (o, value, params) {
                if (value = dasharray[Str(value).toLowerCase()]) {
                    for (var width = o.attrs["stroke-width"] || "1", butt = {
                        round: width,
                        square: width,
                        butt: 0
                    }[o.attrs["stroke-linecap"] || params["stroke-linecap"]] || 0, dashes = [], i = value.length; i--;) dashes[i] = value[i] * width + (i % 2 ? 1 : -1) * butt;
                    $(o.node, {"stroke-dasharray": dashes.join(",")})
                }
            }, setFillAndStroke = function (o, params) {
                var node = o.node, attrs = o.attrs, vis = node.style.visibility;
                node.style.visibility = "hidden";
                for (var att in params) if (params[has](att)) {
                    if (!R._availableAttrs[has](att)) continue;
                    var value = params[att];
                    switch (attrs[att] = value, att) {
                        case"blur":
                            o.blur(value);
                            break;
                        case"href":
                        case"title":
                            var hl = $("title"), val = R._g.doc.createTextNode(value);
                            hl.appendChild(val), node.appendChild(hl);
                            break;
                        case"target":
                            var pn = node.parentNode;
                            if ("a" != pn.tagName.toLowerCase()) {
                                var hl = $("a");
                                pn.insertBefore(hl, node), hl.appendChild(node), pn = hl
                            }
                            "target" == att ? pn.setAttributeNS(xlink, "show", "blank" == value ? "new" : value) : pn.setAttributeNS(xlink, att, value);
                            break;
                        case"cursor":
                            node.style.cursor = value;
                            break;
                        case"transform":
                            o.transform(value);
                            break;
                        case"arrow-start":
                            addArrow(o, value);
                            break;
                        case"arrow-end":
                            addArrow(o, value, 1);
                            break;
                        case"clip-rect":
                            var rect = Str(value).split(separator);
                            if (4 == rect.length) {
                                o.clip && o.clip.parentNode.parentNode.removeChild(o.clip.parentNode);
                                var el = $("clipPath"), rc = $("rect");
                                el.id = R.createUUID(), $(rc, {
                                    x: rect[0],
                                    y: rect[1],
                                    width: rect[2],
                                    height: rect[3]
                                }), el.appendChild(rc), o.paper.defs.appendChild(el), $(node, {"clip-path": "url(#" + el.id + ")"}), o.clip = rc
                            }
                            if (!value) {
                                var path = node.getAttribute("clip-path");
                                if (path) {
                                    var clip = R._g.doc.getElementById(path.replace(/(^url\(#|\)$)/g, E));
                                    clip && clip.parentNode.removeChild(clip), $(node, {"clip-path": E}), delete o.clip
                                }
                            }
                            break;
                        case"path":
                            "path" == o.type && ($(node, {d: value ? attrs.path = R._pathToAbsolute(value) : "M0,0"}), o._.dirty = 1, o._.arrows && ("startString" in o._.arrows && addArrow(o, o._.arrows.startString), "endString" in o._.arrows && addArrow(o, o._.arrows.endString, 1)));
                            break;
                        case"width":
                            if (node.setAttribute(att, value), o._.dirty = 1, !attrs.fx) break;
                            att = "x", value = attrs.x;
                        case"x":
                            attrs.fx && (value = -attrs.x - (attrs.width || 0));
                        case"rx":
                            if ("rx" == att && "rect" == o.type) break;
                        case"cx":
                            node.setAttribute(att, value), o.pattern && updatePosition(o), o._.dirty = 1;
                            break;
                        case"height":
                            if (node.setAttribute(att, value), o._.dirty = 1, !attrs.fy) break;
                            att = "y", value = attrs.y;
                        case"y":
                            attrs.fy && (value = -attrs.y - (attrs.height || 0));
                        case"ry":
                            if ("ry" == att && "rect" == o.type) break;
                        case"cy":
                            node.setAttribute(att, value), o.pattern && updatePosition(o), o._.dirty = 1;
                            break;
                        case"r":
                            "rect" == o.type ? $(node, {
                                rx: value,
                                ry: value
                            }) : node.setAttribute(att, value), o._.dirty = 1;
                            break;
                        case"src":
                            "image" == o.type && node.setAttributeNS(xlink, "href", value);
                            break;
                        case"stroke-width":
                            (1 != o._.sx || 1 != o._.sy) && (value /= mmax(abs(o._.sx), abs(o._.sy)) || 1), o.paper._vbSize && (value *= o.paper._vbSize), node.setAttribute(att, value), attrs["stroke-dasharray"] && addDashes(o, attrs["stroke-dasharray"], params), o._.arrows && ("startString" in o._.arrows && addArrow(o, o._.arrows.startString), "endString" in o._.arrows && addArrow(o, o._.arrows.endString, 1));
                            break;
                        case"stroke-dasharray":
                            addDashes(o, value, params);
                            break;
                        case"fill":
                            var isURL = Str(value).match(R._ISURL);
                            if (isURL) {
                                el = $("pattern");
                                var ig = $("image");
                                el.id = R.createUUID(), $(el, {
                                    x: 0,
                                    y: 0,
                                    patternUnits: "userSpaceOnUse",
                                    height: 1,
                                    width: 1
                                }), $(ig, {x: 0, y: 0, "xlink:href": isURL[1]}), el.appendChild(ig), function (el) {
                                    R._preload(isURL[1], function () {
                                        var w = this.offsetWidth, h = this.offsetHeight;
                                        $(el, {width: w, height: h}), $(ig, {width: w, height: h}), o.paper.safari()
                                    })
                                }(el), o.paper.defs.appendChild(el), $(node, {fill: "url(#" + el.id + ")"}), o.pattern = el, o.pattern && updatePosition(o);
                                break
                            }
                            var clr = R.getRGB(value);
                            if (clr.error) {
                                if (("circle" == o.type || "ellipse" == o.type || "r" != Str(value).charAt()) && addGradientFill(o, value)) {
                                    if ("opacity" in attrs || "fill-opacity" in attrs) {
                                        var gradient = R._g.doc.getElementById(node.getAttribute("fill").replace(/^url\(#|\)$/g, E));
                                        if (gradient) {
                                            var stops = gradient.getElementsByTagName("stop");
                                            $(stops[stops.length - 1], {"stop-opacity": ("opacity" in attrs ? attrs.opacity : 1) * ("fill-opacity" in attrs ? attrs["fill-opacity"] : 1)})
                                        }
                                    }
                                    attrs.gradient = value, attrs.fill = "none";
                                    break
                                }
                            } else delete params.gradient, delete attrs.gradient, !R.is(attrs.opacity, "undefined") && R.is(params.opacity, "undefined") && $(node, {opacity: attrs.opacity}), !R.is(attrs["fill-opacity"], "undefined") && R.is(params["fill-opacity"], "undefined") && $(node, {"fill-opacity": attrs["fill-opacity"]});
                            clr[has]("opacity") && $(node, {"fill-opacity": clr.opacity > 1 ? clr.opacity / 100 : clr.opacity});
                        case"stroke":
                            clr = R.getRGB(value), node.setAttribute(att, clr.hex), "stroke" == att && clr[has]("opacity") && $(node, {"stroke-opacity": clr.opacity > 1 ? clr.opacity / 100 : clr.opacity}), "stroke" == att && o._.arrows && ("startString" in o._.arrows && addArrow(o, o._.arrows.startString), "endString" in o._.arrows && addArrow(o, o._.arrows.endString, 1));
                            break;
                        case"gradient":
                            ("circle" == o.type || "ellipse" == o.type || "r" != Str(value).charAt()) && addGradientFill(o, value);
                            break;
                        case"opacity":
                            attrs.gradient && !attrs[has]("stroke-opacity") && $(node, {"stroke-opacity": value > 1 ? value / 100 : value});
                        case"fill-opacity":
                            if (attrs.gradient) {
                                gradient = R._g.doc.getElementById(node.getAttribute("fill").replace(/^url\(#|\)$/g, E)), gradient && (stops = gradient.getElementsByTagName("stop"), $(stops[stops.length - 1], {"stop-opacity": value}));
                                break
                            }
                        default:
                            "font-size" == att && (value = toInt(value, 10) + "px");
                            var cssrule = att.replace(/(\-.)/g, function (w) {
                                return w.substring(1).toUpperCase()
                            });
                            node.style[cssrule] = value, o._.dirty = 1, node.setAttribute(att, value)
                    }
                }
                tuneText(o, params), node.style.visibility = vis
            }, leading = 1.2, tuneText = function (el, params) {
                if ("text" == el.type && (params[has]("text") || params[has]("font") || params[has]("font-size") || params[has]("x") || params[has]("y"))) {
                    var a = el.attrs, node = el.node,
                        fontSize = node.firstChild ? toInt(R._g.doc.defaultView.getComputedStyle(node.firstChild, E).getPropertyValue("font-size"), 10) : 10;
                    if (params[has]("text")) {
                        for (a.text = params.text; node.firstChild;) node.removeChild(node.firstChild);
                        for (var tspan, texts = Str(params.text).split("\n"), tspans = [], i = 0, ii = texts.length; ii > i; i++) tspan = $("tspan"), i && $(tspan, {
                            dy: fontSize * leading,
                            x: a.x
                        }), tspan.appendChild(R._g.doc.createTextNode(texts[i])), node.appendChild(tspan), tspans[i] = tspan
                    } else for (tspans = node.getElementsByTagName("tspan"), i = 0, ii = tspans.length; ii > i; i++) i ? $(tspans[i], {
                        dy: fontSize * leading,
                        x: a.x
                    }) : $(tspans[0], {dy: 0});
                    $(node, {x: a.x, y: a.y}), el._.dirty = 1;
                    var bb = el._getBBox(), dif = a.y - (bb.y + bb.height / 2);
                    dif && R.is(dif, "finite") && $(tspans[0], {dy: dif})
                }
            }, Element = function (node, svg) {
                this[0] = this.node = node, node.raphael = !0, this.id = R._oid++, node.raphaelid = this.id, this.matrix = R.matrix(), this.realPath = null, this.paper = svg, this.attrs = this.attrs || {}, this._ = {
                    transform: [],
                    sx: 1,
                    sy: 1,
                    deg: 0,
                    dx: 0,
                    dy: 0,
                    dirty: 1
                }, !svg.bottom && (svg.bottom = this), this.prev = svg.top, svg.top && (svg.top.next = this), svg.top = this, this.next = null
            }, elproto = R.el;
            Element.prototype = elproto, elproto.constructor = Element, R._engine.path = function (pathString, SVG) {
                var el = $("path");
                SVG.canvas && SVG.canvas.appendChild(el);
                var p = new Element(el, SVG);
                return p.type = "path", setFillAndStroke(p, {fill: "none", stroke: "#000", path: pathString}), p
            }, elproto.rotate = function (deg, cx, cy) {
                if (this.removed) return this;
                if (deg = Str(deg).split(separator), deg.length - 1 && (cx = toFloat(deg[1]), cy = toFloat(deg[2])), deg = toFloat(deg[0]), null == cy && (cx = cy), null == cx || null == cy) {
                    var bbox = this.getBBox(1);
                    cx = bbox.x + bbox.width / 2, cy = bbox.y + bbox.height / 2
                }
                return this.transform(this._.transform.concat([["r", deg, cx, cy]])), this
            }, elproto.scale = function (sx, sy, cx, cy) {
                if (this.removed) return this;
                if (sx = Str(sx).split(separator), sx.length - 1 && (sy = toFloat(sx[1]), cx = toFloat(sx[2]), cy = toFloat(sx[3])), sx = toFloat(sx[0]), null == sy && (sy = sx), null == cy && (cx = cy), null == cx || null == cy) var bbox = this.getBBox(1);
                return cx = null == cx ? bbox.x + bbox.width / 2 : cx, cy = null == cy ? bbox.y + bbox.height / 2 : cy, this.transform(this._.transform.concat([["s", sx, sy, cx, cy]])), this
            }, elproto.translate = function (dx, dy) {
                return this.removed ? this : (dx = Str(dx).split(separator), dx.length - 1 && (dy = toFloat(dx[1])), dx = toFloat(dx[0]) || 0, dy = +dy || 0, this.transform(this._.transform.concat([["t", dx, dy]])), this)
            }, elproto.transform = function (tstr) {
                var _ = this._;
                if (null == tstr) return _.transform;
                if (R._extractTransform(this, tstr), this.clip && $(this.clip, {transform: this.matrix.invert()}), this.pattern && updatePosition(this), this.node && $(this.node, {transform: this.matrix}), 1 != _.sx || 1 != _.sy) {
                    var sw = this.attrs[has]("stroke-width") ? this.attrs["stroke-width"] : 1;
                    this.attr({"stroke-width": sw})
                }
                return this
            }, elproto.hide = function () {
                return !this.removed && this.paper.safari(this.node.style.display = "none"), this
            }, elproto.show = function () {
                return !this.removed && this.paper.safari(this.node.style.display = ""), this
            }, elproto.remove = function () {
                if (!this.removed && this.node.parentNode) {
                    var paper = this.paper;
                    paper.__set__ && paper.__set__.exclude(this), eve.unbind("raphael.*.*." + this.id), this.gradient && paper.defs.removeChild(this.gradient), R._tear(this, paper), "a" == this.node.parentNode.tagName.toLowerCase() ? this.node.parentNode.parentNode.removeChild(this.node.parentNode) : this.node.parentNode.removeChild(this.node);
                    for (var i in this) this[i] = "function" == typeof this[i] ? R._removedFactory(i) : null;
                    this.removed = !0
                }
            }, elproto._getBBox = function () {
                if ("none" == this.node.style.display) {
                    this.show();
                    var hide = !0
                }
                var bbox = {};
                try {
                    bbox = this.node.getBBox()
                } catch (e) {
                } finally {
                    bbox = bbox || {}
                }
                return hide && this.hide(), bbox
            }, elproto.attr = function (name, value) {
                if (this.removed) return this;
                if (null == name) {
                    var res = {};
                    for (var a in this.attrs) this.attrs[has](a) && (res[a] = this.attrs[a]);
                    return res.gradient && "none" == res.fill && (res.fill = res.gradient) && delete res.gradient, res.transform = this._.transform, res
                }
                if (null == value && R.is(name, "string")) {
                    if ("fill" == name && "none" == this.attrs.fill && this.attrs.gradient) return this.attrs.gradient;
                    if ("transform" == name) return this._.transform;
                    for (var names = name.split(separator), out = {}, i = 0, ii = names.length; ii > i; i++) name = names[i], out[name] = name in this.attrs ? this.attrs[name] : R.is(this.paper.customAttributes[name], "function") ? this.paper.customAttributes[name].def : R._availableAttrs[name];
                    return ii - 1 ? out : out[names[0]]
                }
                if (null == value && R.is(name, "array")) {
                    for (out = {}, i = 0, ii = name.length; ii > i; i++) out[name[i]] = this.attr(name[i]);
                    return out
                }
                if (null != value) {
                    var params = {};
                    params[name] = value
                } else null != name && R.is(name, "object") && (params = name);
                for (var key in params) eve("raphael.attr." + key + "." + this.id, this, params[key]);
                for (key in this.paper.customAttributes) if (this.paper.customAttributes[has](key) && params[has](key) && R.is(this.paper.customAttributes[key], "function")) {
                    var par = this.paper.customAttributes[key].apply(this, [].concat(params[key]));
                    this.attrs[key] = params[key];
                    for (var subkey in par) par[has](subkey) && (params[subkey] = par[subkey])
                }
                return setFillAndStroke(this, params), this
            }, elproto.toFront = function () {
                if (this.removed) return this;
                "a" == this.node.parentNode.tagName.toLowerCase() ? this.node.parentNode.parentNode.appendChild(this.node.parentNode) : this.node.parentNode.appendChild(this.node);
                var svg = this.paper;
                return svg.top != this && R._tofront(this, svg), this
            }, elproto.toBack = function () {
                if (this.removed) return this;
                var parent = this.node.parentNode;
                "a" == parent.tagName.toLowerCase() ? parent.parentNode.insertBefore(this.node.parentNode, this.node.parentNode.parentNode.firstChild) : parent.firstChild != this.node && parent.insertBefore(this.node, this.node.parentNode.firstChild), R._toback(this, this.paper);
                this.paper;
                return this
            }, elproto.insertAfter = function (element) {
                if (this.removed) return this;
                var node = element.node || element[element.length - 1].node;
                return node.nextSibling ? node.parentNode.insertBefore(this.node, node.nextSibling) : node.parentNode.appendChild(this.node), R._insertafter(this, element, this.paper), this
            }, elproto.insertBefore = function (element) {
                if (this.removed) return this;
                var node = element.node || element[0].node;
                return node.parentNode.insertBefore(this.node, node), R._insertbefore(this, element, this.paper), this
            }, elproto.blur = function (size) {
                var t = this;
                if (0 !== +size) {
                    var fltr = $("filter"), blur = $("feGaussianBlur");
                    t.attrs.blur = size, fltr.id = R.createUUID(), $(blur, {stdDeviation: +size || 1.5}), fltr.appendChild(blur), t.paper.defs.appendChild(fltr), t._blur = fltr, $(t.node, {filter: "url(#" + fltr.id + ")"})
                } else t._blur && (t._blur.parentNode.removeChild(t._blur), delete t._blur, delete t.attrs.blur), t.node.removeAttribute("filter");
                return t
            }, R._engine.circle = function (svg, x, y, r) {
                var el = $("circle");
                svg.canvas && svg.canvas.appendChild(el);
                var res = new Element(el, svg);
                return res.attrs = {
                    cx: x,
                    cy: y,
                    r: r,
                    fill: "none",
                    stroke: "#000"
                }, res.type = "circle", $(el, res.attrs), res
            }, R._engine.rect = function (svg, x, y, w, h, r) {
                var el = $("rect");
                svg.canvas && svg.canvas.appendChild(el);
                var res = new Element(el, svg);
                return res.attrs = {
                    x: x,
                    y: y,
                    width: w,
                    height: h,
                    r: r || 0,
                    rx: r || 0,
                    ry: r || 0,
                    fill: "none",
                    stroke: "#000"
                }, res.type = "rect", $(el, res.attrs), res
            }, R._engine.ellipse = function (svg, x, y, rx, ry) {
                var el = $("ellipse");
                svg.canvas && svg.canvas.appendChild(el);
                var res = new Element(el, svg);
                return res.attrs = {
                    cx: x,
                    cy: y,
                    rx: rx,
                    ry: ry,
                    fill: "none",
                    stroke: "#000"
                }, res.type = "ellipse", $(el, res.attrs), res
            }, R._engine.image = function (svg, src, x, y, w, h) {
                var el = $("image");
                $(el, {
                    x: x,
                    y: y,
                    width: w,
                    height: h,
                    preserveAspectRatio: "none"
                }), el.setAttributeNS(xlink, "href", src), svg.canvas && svg.canvas.appendChild(el);
                var res = new Element(el, svg);
                return res.attrs = {x: x, y: y, width: w, height: h, src: src}, res.type = "image", res
            }, R._engine.text = function (svg, x, y, text) {
                var el = $("text");
                svg.canvas && svg.canvas.appendChild(el);
                var res = new Element(el, svg);
                return res.attrs = {
                    x: x,
                    y: y,
                    "text-anchor": "middle",
                    text: text,
                    font: R._availableAttrs.font,
                    stroke: "none",
                    fill: "#000"
                }, res.type = "text", setFillAndStroke(res, res.attrs), res
            }, R._engine.setSize = function (width, height) {
                return this.width = width || this.width, this.height = height || this.height, this.canvas.setAttribute("width", this.width), this.canvas.setAttribute("height", this.height), this._viewBox && this.setViewBox.apply(this, this._viewBox), this
            }, R._engine.create = function () {
                var con = R._getContainer.apply(0, arguments), container = con && con.container, x = con.x, y = con.y,
                    width = con.width, height = con.height;
                if (!container) throw new Error("SVG container not found.");
                var isFloating, cnvs = $("svg"), css = "overflow:hidden;";
                return x = x || 0, y = y || 0, width = width || 512, height = height || 342, $(cnvs, {
                    height: height,
                    version: 1.1,
                    width: width,
                    xmlns: "http://www.w3.org/2000/svg"
                }), 1 == container ? (cnvs.style.cssText = css + "position:absolute;left:" + x + "px;top:" + y + "px", R._g.doc.body.appendChild(cnvs), isFloating = 1) : (cnvs.style.cssText = css + "position:relative", container.firstChild ? container.insertBefore(cnvs, container.firstChild) : container.appendChild(cnvs)), container = new R._Paper, container.width = width, container.height = height, container.canvas = cnvs, container.clear(), container._left = container._top = 0, isFloating && (container.renderfix = function () {
                }), container.renderfix(), container
            }, R._engine.setViewBox = function (x, y, w, h, fit) {
                eve("raphael.setViewBox", this, this._viewBox, [x, y, w, h, fit]);
                var vb, sw, size = mmax(w / this.width, h / this.height), top = this.top,
                    aspectRatio = fit ? "meet" : "xMinYMin";
                for (null == x ? (this._vbSize && (size = 1), delete this._vbSize, vb = "0 0 " + this.width + S + this.height) : (this._vbSize = size, vb = x + S + y + S + w + S + h), $(this.canvas, {
                    viewBox: vb,
                    preserveAspectRatio: aspectRatio
                }); size && top;) sw = "stroke-width" in top.attrs ? top.attrs["stroke-width"] : 1, top.attr({"stroke-width": sw}), top._.dirty = 1, top._.dirtyT = 1, top = top.prev;
                return this._viewBox = [x, y, w, h, !!fit], this
            }, R.prototype.renderfix = function () {
                var pos, cnvs = this.canvas, s = cnvs.style;
                try {
                    pos = cnvs.getScreenCTM() || cnvs.createSVGMatrix()
                } catch (e) {
                    pos = cnvs.createSVGMatrix()
                }
                var left = -pos.e % 1, top = -pos.f % 1;
                (left || top) && (left && (this._left = (this._left + left) % 1, s.left = this._left + "px"), top && (this._top = (this._top + top) % 1, s.top = this._top + "px"))
            }, R.prototype.clear = function () {
                R.eve("raphael.clear", this);
                for (var c = this.canvas; c.firstChild;) c.removeChild(c.firstChild);
                this.bottom = this.top = null, (this.desc = $("desc")).appendChild(R._g.doc.createTextNode("Created with Rapha毛l " + R.version)), c.appendChild(this.desc), c.appendChild(this.defs = $("defs"))
            }, R.prototype.remove = function () {
                eve("raphael.remove", this), this.canvas.parentNode && this.canvas.parentNode.removeChild(this.canvas);
                for (var i in this) this[i] = "function" == typeof this[i] ? R._removedFactory(i) : null
            };
            var setproto = R.st;
            for (var method in elproto) elproto[has](method) && !setproto[has](method) && (setproto[method] = function (methodname) {
                return function () {
                    var arg = arguments;
                    return this.forEach(function (el) {
                        el[methodname].apply(el, arg)
                    })
                }
            }(method))
        }
    }(), function () {
        if (R.vml) {
            var has = "hasOwnProperty", Str = String, toFloat = parseFloat, math = Math, round = math.round,
                mmax = math.max, mmin = math.min, abs = math.abs, fillString = "fill", separator = /[, ]+/, eve = R.eve,
                ms = " progid:DXImageTransform.Microsoft", S = " ", E = "",
                map = {M: "m", L: "l", C: "c", Z: "x", m: "t", l: "r", c: "v", z: "x"},
                bites = /([clmz]),?([^clmz]*)/gi, blurregexp = / progid:\S+Blur\([^\)]+\)/g, val = /-?[^,\s-]+/g,
                cssDot = "position:absolute;left:0;top:0;width:1px;height:1px", zoom = 21600,
                pathTypes = {path: 1, rect: 1, image: 1}, ovalTypes = {circle: 1, ellipse: 1},
                path2vml = function (path) {
                    var total = /[ahqstv]/gi, command = R._pathToAbsolute;
                    if (Str(path).match(total) && (command = R._path2curve), total = /[clmz]/g, command == R._pathToAbsolute && !Str(path).match(total)) {
                        var res = Str(path).replace(bites, function (all, command, args) {
                            var vals = [], isMove = "m" == command.toLowerCase(), res = map[command];
                            return args.replace(val, function (value) {
                                isMove && 2 == vals.length && (res += vals + map["m" == command ? "l" : "L"], vals = []), vals.push(round(value * zoom))
                            }), res + vals
                        });
                        return res
                    }
                    var p, r, pa = command(path);
                    res = [];
                    for (var i = 0, ii = pa.length; ii > i; i++) {
                        p = pa[i], r = pa[i][0].toLowerCase(), "z" == r && (r = "x");
                        for (var j = 1, jj = p.length; jj > j; j++) r += round(p[j] * zoom) + (j != jj - 1 ? "," : E);
                        res.push(r)
                    }
                    return res.join(S)
                }, compensation = function (deg, dx, dy) {
                    var m = R.matrix();
                    return m.rotate(-deg, .5, .5), {dx: m.x(dx, dy), dy: m.y(dx, dy)}
                }, setCoords = function (p, sx, sy, dx, dy, deg) {
                    var _ = p._, m = p.matrix, fillpos = _.fillpos, o = p.node, s = o.style, y = 1, flip = "",
                        kx = zoom / sx, ky = zoom / sy;
                    if (s.visibility = "hidden", sx && sy) {
                        if (o.coordsize = abs(kx) + S + abs(ky), s.rotation = deg * (0 > sx * sy ? -1 : 1), deg) {
                            var c = compensation(deg, dx, dy);
                            dx = c.dx, dy = c.dy
                        }
                        if (0 > sx && (flip += "x"), 0 > sy && (flip += " y") && (y = -1), s.flip = flip, o.coordorigin = dx * -kx + S + dy * -ky, fillpos || _.fillsize) {
                            var fill = o.getElementsByTagName(fillString);
                            fill = fill && fill[0], o.removeChild(fill), fillpos && (c = compensation(deg, m.x(fillpos[0], fillpos[1]), m.y(fillpos[0], fillpos[1])), fill.position = c.dx * y + S + c.dy * y), _.fillsize && (fill.size = _.fillsize[0] * abs(sx) + S + _.fillsize[1] * abs(sy)), o.appendChild(fill)
                        }
                        s.visibility = "visible"
                    }
                };
            R.toString = function () {
                return "Your browser doesn鈥檛 support SVG. Falling down to VML.\nYou are running Rapha毛l " + this.version
            };
            var addArrow = function (o, value, isEnd) {
                for (var values = Str(value).toLowerCase().split("-"), se = isEnd ? "end" : "start", i = values.length, type = "classic", w = "medium", h = "medium"; i--;) switch (values[i]) {
                    case"block":
                    case"classic":
                    case"oval":
                    case"diamond":
                    case"open":
                    case"none":
                        type = values[i];
                        break;
                    case"wide":
                    case"narrow":
                        h = values[i];
                        break;
                    case"long":
                    case"short":
                        w = values[i]
                }
                var stroke = o.node.getElementsByTagName("stroke")[0];
                stroke[se + "arrow"] = type, stroke[se + "arrowlength"] = w, stroke[se + "arrowwidth"] = h
            }, setFillAndStroke = function (o, params) {
                o.attrs = o.attrs || {};
                var node = o.node, a = o.attrs, s = node.style,
                    newpath = pathTypes[o.type] && (params.x != a.x || params.y != a.y || params.width != a.width || params.height != a.height || params.cx != a.cx || params.cy != a.cy || params.rx != a.rx || params.ry != a.ry || params.r != a.r),
                    isOval = ovalTypes[o.type] && (a.cx != params.cx || a.cy != params.cy || a.r != params.r || a.rx != params.rx || a.ry != params.ry),
                    res = o;
                for (var par in params) params[has](par) && (a[par] = params[par]);
                if (newpath && (a.path = R._getPath[o.type](o), o._.dirty = 1), params.href && (node.href = params.href), params.title && (node.title = params.title), params.target && (node.target = params.target), params.cursor && (s.cursor = params.cursor), "blur" in params && o.blur(params.blur), (params.path && "path" == o.type || newpath) && (node.path = path2vml(~Str(a.path).toLowerCase().indexOf("r") ? R._pathToAbsolute(a.path) : a.path), "image" == o.type && (o._.fillpos = [a.x, a.y], o._.fillsize = [a.width, a.height], setCoords(o, 1, 1, 0, 0, 0))), "transform" in params && o.transform(params.transform), isOval) {
                    var cx = +a.cx, cy = +a.cy, rx = +a.rx || +a.r || 0, ry = +a.ry || +a.r || 0;
                    node.path = R.format("ar{0},{1},{2},{3},{4},{1},{4},{1}x", round((cx - rx) * zoom), round((cy - ry) * zoom), round((cx + rx) * zoom), round((cy + ry) * zoom), round(cx * zoom)), o._.dirty = 1
                }
                if ("clip-rect" in params) {
                    var rect = Str(params["clip-rect"]).split(separator);
                    if (4 == rect.length) {
                        rect[2] = +rect[2] + +rect[0], rect[3] = +rect[3] + +rect[1];
                        var div = node.clipRect || R._g.doc.createElement("div"), dstyle = div.style;
                        dstyle.clip = R.format("rect({1}px {2}px {3}px {0}px)", rect), node.clipRect || (dstyle.position = "absolute", dstyle.top = 0, dstyle.left = 0, dstyle.width = o.paper.width + "px", dstyle.height = o.paper.height + "px", node.parentNode.insertBefore(div, node), div.appendChild(node), node.clipRect = div)
                    }
                    params["clip-rect"] || node.clipRect && (node.clipRect.style.clip = "auto")
                }
                if (o.textpath) {
                    var textpathStyle = o.textpath.style;
                    params.font && (textpathStyle.font = params.font), params["font-family"] && (textpathStyle.fontFamily = '"' + params["font-family"].split(",")[0].replace(/^['"]+|['"]+$/g, E) + '"'), params["font-size"] && (textpathStyle.fontSize = params["font-size"]), params["font-weight"] && (textpathStyle.fontWeight = params["font-weight"]), params["font-style"] && (textpathStyle.fontStyle = params["font-style"])
                }
                if ("arrow-start" in params && addArrow(res, params["arrow-start"]), "arrow-end" in params && addArrow(res, params["arrow-end"], 1), null != params.opacity || null != params["stroke-width"] || null != params.fill || null != params.src || null != params.stroke || null != params["stroke-width"] || null != params["stroke-opacity"] || null != params["fill-opacity"] || null != params["stroke-dasharray"] || null != params["stroke-miterlimit"] || null != params["stroke-linejoin"] || null != params["stroke-linecap"]) {
                    var fill = node.getElementsByTagName(fillString), newfill = !1;
                    if (fill = fill && fill[0], !fill && (newfill = fill = createNode(fillString)), "image" == o.type && params.src && (fill.src = params.src), params.fill && (fill.on = !0), (null == fill.on || "none" == params.fill || null === params.fill) && (fill.on = !1), fill.on && params.fill) {
                        var isURL = Str(params.fill).match(R._ISURL);
                        if (isURL) {
                            fill.parentNode == node && node.removeChild(fill), fill.rotate = !0, fill.src = isURL[1], fill.type = "tile";
                            var bbox = o.getBBox(1);
                            fill.position = bbox.x + S + bbox.y, o._.fillpos = [bbox.x, bbox.y], R._preload(isURL[1], function () {
                                o._.fillsize = [this.offsetWidth, this.offsetHeight]
                            })
                        } else fill.color = R.getRGB(params.fill).hex, fill.src = E, fill.type = "solid", R.getRGB(params.fill).error && (res.type in {
                            circle: 1,
                            ellipse: 1
                        } || "r" != Str(params.fill).charAt()) && addGradientFill(res, params.fill, fill) && (a.fill = "none", a.gradient = params.fill, fill.rotate = !1)
                    }
                    if ("fill-opacity" in params || "opacity" in params) {
                        var opacity = ((+a["fill-opacity"] + 1 || 2) - 1) * ((+a.opacity + 1 || 2) - 1) * ((+R.getRGB(params.fill).o + 1 || 2) - 1);
                        opacity = mmin(mmax(opacity, 0), 1), fill.opacity = opacity, fill.src && (fill.color = "none")
                    }
                    node.appendChild(fill);
                    var stroke = node.getElementsByTagName("stroke") && node.getElementsByTagName("stroke")[0],
                        newstroke = !1;
                    !stroke && (newstroke = stroke = createNode("stroke")), (params.stroke && "none" != params.stroke || params["stroke-width"] || null != params["stroke-opacity"] || params["stroke-dasharray"] || params["stroke-miterlimit"] || params["stroke-linejoin"] || params["stroke-linecap"]) && (stroke.on = !0), ("none" == params.stroke || null === params.stroke || null == stroke.on || 0 == params.stroke || 0 == params["stroke-width"]) && (stroke.on = !1);
                    var strokeColor = R.getRGB(params.stroke);
                    stroke.on && params.stroke && (stroke.color = strokeColor.hex), opacity = ((+a["stroke-opacity"] + 1 || 2) - 1) * ((+a.opacity + 1 || 2) - 1) * ((+strokeColor.o + 1 || 2) - 1);
                    var width = .75 * (toFloat(params["stroke-width"]) || 1);
                    if (opacity = mmin(mmax(opacity, 0), 1), null == params["stroke-width"] && (width = a["stroke-width"]), params["stroke-width"] && (stroke.weight = width), width && 1 > width && (opacity *= width) && (stroke.weight = 1), stroke.opacity = opacity, params["stroke-linejoin"] && (stroke.joinstyle = params["stroke-linejoin"] || "miter"), stroke.miterlimit = params["stroke-miterlimit"] || 8, params["stroke-linecap"] && (stroke.endcap = "butt" == params["stroke-linecap"] ? "flat" : "square" == params["stroke-linecap"] ? "square" : "round"), params["stroke-dasharray"]) {
                        var dasharray = {
                            "-": "shortdash",
                            ".": "shortdot",
                            "-.": "shortdashdot",
                            "-..": "shortdashdotdot",
                            ". ": "dot",
                            "- ": "dash",
                            "--": "longdash",
                            "- .": "dashdot",
                            "--.": "longdashdot",
                            "--..": "longdashdotdot"
                        };
                        stroke.dashstyle = dasharray[has](params["stroke-dasharray"]) ? dasharray[params["stroke-dasharray"]] : E
                    }
                    newstroke && node.appendChild(stroke)
                }
                if ("text" == res.type) {
                    res.paper.canvas.style.display = E;
                    var span = res.paper.span, m = 100, fontSize = a.font && a.font.match(/\d+(?:\.\d*)?(?=px)/);
                    s = span.style, a.font && (s.font = a.font), a["font-family"] && (s.fontFamily = a["font-family"]), a["font-weight"] && (s.fontWeight = a["font-weight"]), a["font-style"] && (s.fontStyle = a["font-style"]), fontSize = toFloat(a["font-size"] || fontSize && fontSize[0]) || 10, s.fontSize = fontSize * m + "px", res.textpath.string && (span.innerHTML = Str(res.textpath.string).replace(/</g, "&#60;").replace(/&/g, "&#38;").replace(/\n/g, "<br>"));
                    var brect = span.getBoundingClientRect();
                    res.W = a.w = (brect.right - brect.left) / m, res.H = a.h = (brect.bottom - brect.top) / m, res.X = a.x, res.Y = a.y + res.H / 2, ("x" in params || "y" in params) && (res.path.v = R.format("m{0},{1}l{2},{1}", round(a.x * zoom), round(a.y * zoom), round(a.x * zoom) + 1));
                    for (var dirtyattrs = ["x", "y", "text", "font", "font-family", "font-weight", "font-style", "font-size"], d = 0, dd = dirtyattrs.length; dd > d; d++) if (dirtyattrs[d] in params) {
                        res._.dirty = 1;
                        break
                    }
                    switch (a["text-anchor"]) {
                        case"start":
                            res.textpath.style["v-text-align"] = "left", res.bbx = res.W / 2;
                            break;
                        case"end":
                            res.textpath.style["v-text-align"] = "right", res.bbx = -res.W / 2;
                            break;
                        default:
                            res.textpath.style["v-text-align"] = "center", res.bbx = 0
                    }
                    res.textpath.style["v-text-kern"] = !0
                }
            }, addGradientFill = function (o, gradient, fill) {
                o.attrs = o.attrs || {};
                var pow = (o.attrs, Math.pow), type = "linear", fxfy = ".5 .5";
                if (o.attrs.gradient = gradient, gradient = Str(gradient).replace(R._radial_gradient, function (all, fx, fy) {
                    return type = "radial", fx && fy && (fx = toFloat(fx), fy = toFloat(fy), pow(fx - .5, 2) + pow(fy - .5, 2) > .25 && (fy = math.sqrt(.25 - pow(fx - .5, 2)) * (2 * (fy > .5) - 1) + .5), fxfy = fx + S + fy), E
                }), gradient = gradient.split(/\s*\-\s*/), "linear" == type) {
                    var angle = gradient.shift();
                    if (angle = -toFloat(angle), isNaN(angle)) return null
                }
                var dots = R._parseDots(gradient);
                if (!dots) return null;
                if (o = o.shape || o.node, dots.length) {
                    o.removeChild(fill), fill.on = !0, fill.method = "none", fill.color = dots[0].color, fill.color2 = dots[dots.length - 1].color;
                    for (var clrs = [], i = 0, ii = dots.length; ii > i; i++) dots[i].offset && clrs.push(dots[i].offset + S + dots[i].color);
                    fill.colors = clrs.length ? clrs.join() : "0% " + fill.color, "radial" == type ? (fill.type = "gradientTitle", fill.focus = "100%", fill.focussize = "0 0", fill.focusposition = fxfy, fill.angle = 0) : (fill.type = "gradient", fill.angle = (270 - angle) % 360), o.appendChild(fill)
                }
                return 1
            }, Element = function (node, vml) {
                this[0] = this.node = node, node.raphael = !0, this.id = R._oid++, node.raphaelid = this.id, this.X = 0, this.Y = 0, this.attrs = {}, this.paper = vml, this.matrix = R.matrix(), this._ = {
                    transform: [],
                    sx: 1,
                    sy: 1,
                    dx: 0,
                    dy: 0,
                    deg: 0,
                    dirty: 1,
                    dirtyT: 1
                }, !vml.bottom && (vml.bottom = this), this.prev = vml.top, vml.top && (vml.top.next = this), vml.top = this, this.next = null
            }, elproto = R.el;
            Element.prototype = elproto, elproto.constructor = Element, elproto.transform = function (tstr) {
                if (null == tstr) return this._.transform;
                var oldt, vbs = this.paper._viewBoxShift,
                    vbt = vbs ? "s" + [vbs.scale, vbs.scale] + "-1-1t" + [vbs.dx, vbs.dy] : E;
                vbs && (oldt = tstr = Str(tstr).replace(/\.{3}|\u2026/g, this._.transform || E)), R._extractTransform(this, vbt + tstr);
                var split, matrix = this.matrix.clone(), skew = this.skew, o = this.node,
                    isGrad = ~Str(this.attrs.fill).indexOf("-"), isPatt = !Str(this.attrs.fill).indexOf("url(");
                if (matrix.translate(1, 1), isPatt || isGrad || "image" == this.type) if (skew.matrix = "1 0 0 1", skew.offset = "0 0", split = matrix.split(), isGrad && split.noRotation || !split.isSimple) {
                    o.style.filter = matrix.toFilter();
                    var bb = this.getBBox(), bbt = this.getBBox(1), dx = bb.x - bbt.x, dy = bb.y - bbt.y;
                    o.coordorigin = dx * -zoom + S + dy * -zoom, setCoords(this, 1, 1, dx, dy, 0)
                } else o.style.filter = E, setCoords(this, split.scalex, split.scaley, split.dx, split.dy, split.rotate); else o.style.filter = E, skew.matrix = Str(matrix), skew.offset = matrix.offset();
                return oldt && (this._.transform = oldt), this
            }, elproto.rotate = function (deg, cx, cy) {
                if (this.removed) return this;
                if (null != deg) {
                    if (deg = Str(deg).split(separator), deg.length - 1 && (cx = toFloat(deg[1]), cy = toFloat(deg[2])), deg = toFloat(deg[0]), null == cy && (cx = cy), null == cx || null == cy) {
                        var bbox = this.getBBox(1);
                        cx = bbox.x + bbox.width / 2, cy = bbox.y + bbox.height / 2
                    }
                    return this._.dirtyT = 1, this.transform(this._.transform.concat([["r", deg, cx, cy]])), this
                }
            }, elproto.translate = function (dx, dy) {
                return this.removed ? this : (dx = Str(dx).split(separator), dx.length - 1 && (dy = toFloat(dx[1])), dx = toFloat(dx[0]) || 0, dy = +dy || 0, this._.bbox && (this._.bbox.x += dx, this._.bbox.y += dy), this.transform(this._.transform.concat([["t", dx, dy]])), this)
            }, elproto.scale = function (sx, sy, cx, cy) {
                if (this.removed) return this;
                if (sx = Str(sx).split(separator), sx.length - 1 && (sy = toFloat(sx[1]), cx = toFloat(sx[2]), cy = toFloat(sx[3]), isNaN(cx) && (cx = null), isNaN(cy) && (cy = null)), sx = toFloat(sx[0]), null == sy && (sy = sx), null == cy && (cx = cy), null == cx || null == cy) var bbox = this.getBBox(1);
                return cx = null == cx ? bbox.x + bbox.width / 2 : cx, cy = null == cy ? bbox.y + bbox.height / 2 : cy, this.transform(this._.transform.concat([["s", sx, sy, cx, cy]])), this._.dirtyT = 1, this
            }, elproto.hide = function () {
                return !this.removed && (this.node.style.display = "none"), this
            }, elproto.show = function () {
                return !this.removed && (this.node.style.display = E), this
            }, elproto._getBBox = function () {
                return this.removed ? {} : {
                    x: this.X + (this.bbx || 0) - this.W / 2,
                    y: this.Y - this.H,
                    width: this.W,
                    height: this.H
                }
            }, elproto.remove = function () {
                if (!this.removed && this.node.parentNode) {
                    this.paper.__set__ && this.paper.__set__.exclude(this), R.eve.unbind("raphael.*.*." + this.id), R._tear(this, this.paper), this.node.parentNode.removeChild(this.node), this.shape && this.shape.parentNode.removeChild(this.shape);
                    for (var i in this) this[i] = "function" == typeof this[i] ? R._removedFactory(i) : null;
                    this.removed = !0
                }
            }, elproto.attr = function (name, value) {
                if (this.removed) return this;
                if (null == name) {
                    var res = {};
                    for (var a in this.attrs) this.attrs[has](a) && (res[a] = this.attrs[a]);
                    return res.gradient && "none" == res.fill && (res.fill = res.gradient) && delete res.gradient, res.transform = this._.transform, res
                }
                if (null == value && R.is(name, "string")) {
                    if (name == fillString && "none" == this.attrs.fill && this.attrs.gradient) return this.attrs.gradient;
                    for (var names = name.split(separator), out = {}, i = 0, ii = names.length; ii > i; i++) name = names[i], out[name] = name in this.attrs ? this.attrs[name] : R.is(this.paper.customAttributes[name], "function") ? this.paper.customAttributes[name].def : R._availableAttrs[name];
                    return ii - 1 ? out : out[names[0]]
                }
                if (this.attrs && null == value && R.is(name, "array")) {
                    for (out = {}, i = 0, ii = name.length; ii > i; i++) out[name[i]] = this.attr(name[i]);
                    return out
                }
                var params;
                null != value && (params = {}, params[name] = value), null == value && R.is(name, "object") && (params = name);
                for (var key in params) eve("raphael.attr." + key + "." + this.id, this, params[key]);
                if (params) {
                    for (key in this.paper.customAttributes) if (this.paper.customAttributes[has](key) && params[has](key) && R.is(this.paper.customAttributes[key], "function")) {
                        var par = this.paper.customAttributes[key].apply(this, [].concat(params[key]));
                        this.attrs[key] = params[key];
                        for (var subkey in par) par[has](subkey) && (params[subkey] = par[subkey])
                    }
                    params.text && "text" == this.type && (this.textpath.string = params.text), setFillAndStroke(this, params)
                }
                return this
            }, elproto.toFront = function () {
                return !this.removed && this.node.parentNode.appendChild(this.node), this.paper && this.paper.top != this && R._tofront(this, this.paper), this
            }, elproto.toBack = function () {
                return this.removed ? this : (this.node.parentNode.firstChild != this.node && (this.node.parentNode.insertBefore(this.node, this.node.parentNode.firstChild), R._toback(this, this.paper)), this)
            }, elproto.insertAfter = function (element) {
                return this.removed ? this : (element.constructor == R.st.constructor && (element = element[element.length - 1]), element.node.nextSibling ? element.node.parentNode.insertBefore(this.node, element.node.nextSibling) : element.node.parentNode.appendChild(this.node), R._insertafter(this, element, this.paper), this)
            }, elproto.insertBefore = function (element) {
                return this.removed ? this : (element.constructor == R.st.constructor && (element = element[0]), element.node.parentNode.insertBefore(this.node, element.node), R._insertbefore(this, element, this.paper), this)
            }, elproto.blur = function (size) {
                var s = this.node.runtimeStyle, f = s.filter;
                return f = f.replace(blurregexp, E), 0 !== +size ? (this.attrs.blur = size, s.filter = f + S + ms + ".Blur(pixelradius=" + (+size || 1.5) + ")", s.margin = R.format("-{0}px 0 0 -{0}px", round(+size || 1.5))) : (s.filter = f, s.margin = 0, delete this.attrs.blur), this
            }, R._engine.path = function (pathString, vml) {
                var el = createNode("shape");
                el.style.cssText = cssDot, el.coordsize = zoom + S + zoom, el.coordorigin = vml.coordorigin;
                var p = new Element(el, vml), attr = {fill: "none", stroke: "#000"};
                pathString && (attr.path = pathString), p.type = "path", p.path = [], p.Path = E, setFillAndStroke(p, attr), vml.canvas.appendChild(el);
                var skew = createNode("skew");
                return skew.on = !0, el.appendChild(skew), p.skew = skew, p.transform(E), p
            }, R._engine.rect = function (vml, x, y, w, h, r) {
                var path = R._rectPath(x, y, w, h, r), res = vml.path(path), a = res.attrs;
                return res.X = a.x = x, res.Y = a.y = y, res.W = a.width = w, res.H = a.height = h, a.r = r, a.path = path, res.type = "rect", res
            }, R._engine.ellipse = function (vml, x, y, rx, ry) {
                {
                    var res = vml.path();
                    res.attrs
                }
                return res.X = x - rx, res.Y = y - ry, res.W = 2 * rx, res.H = 2 * ry, res.type = "ellipse", setFillAndStroke(res, {
                    cx: x,
                    cy: y,
                    rx: rx,
                    ry: ry
                }), res
            }, R._engine.circle = function (vml, x, y, r) {
                {
                    var res = vml.path();
                    res.attrs
                }
                return res.X = x - r, res.Y = y - r, res.W = res.H = 2 * r, res.type = "circle", setFillAndStroke(res, {
                    cx: x,
                    cy: y,
                    r: r
                }), res
            }, R._engine.image = function (vml, src, x, y, w, h) {
                var path = R._rectPath(x, y, w, h), res = vml.path(path).attr({stroke: "none"}), a = res.attrs,
                    node = res.node, fill = node.getElementsByTagName(fillString)[0];
                return a.src = src, res.X = a.x = x, res.Y = a.y = y, res.W = a.width = w, res.H = a.height = h, a.path = path, res.type = "image", fill.parentNode == node && node.removeChild(fill), fill.rotate = !0, fill.src = src, fill.type = "tile", res._.fillpos = [x, y], res._.fillsize = [w, h], node.appendChild(fill), setCoords(res, 1, 1, 0, 0, 0), res
            }, R._engine.text = function (vml, x, y, text) {
                var el = createNode("shape"), path = createNode("path"), o = createNode("textpath");
                x = x || 0, y = y || 0, text = text || "", path.v = R.format("m{0},{1}l{2},{1}", round(x * zoom), round(y * zoom), round(x * zoom) + 1), path.textpathok = !0, o.string = Str(text), o.on = !0, el.style.cssText = cssDot, el.coordsize = zoom + S + zoom, el.coordorigin = "0 0";
                var p = new Element(el, vml),
                    attr = {fill: "#000", stroke: "none", font: R._availableAttrs.font, text: text};
                p.shape = el, p.path = path, p.textpath = o, p.type = "text", p.attrs.text = Str(text), p.attrs.x = x, p.attrs.y = y, p.attrs.w = 1, p.attrs.h = 1, setFillAndStroke(p, attr), el.appendChild(o), el.appendChild(path), vml.canvas.appendChild(el);
                var skew = createNode("skew");
                return skew.on = !0, el.appendChild(skew), p.skew = skew, p.transform(E), p
            }, R._engine.setSize = function (width, height) {
                var cs = this.canvas.style;
                return this.width = width, this.height = height, width == +width && (width += "px"), height == +height && (height += "px"), cs.width = width, cs.height = height, cs.clip = "rect(0 " + width + " " + height + " 0)", this._viewBox && R._engine.setViewBox.apply(this, this._viewBox), this
            }, R._engine.setViewBox = function (x, y, w, h, fit) {
                R.eve("raphael.setViewBox", this, this._viewBox, [x, y, w, h, fit]);
                var H, W, width = this.width, height = this.height, size = 1 / mmax(w / width, h / height);
                return fit && (H = height / h, W = width / w, width > w * H && (x -= (width - w * H) / 2 / H), height > h * W && (y -= (height - h * W) / 2 / W)), this._viewBox = [x, y, w, h, !!fit], this._viewBoxShift = {
                    dx: -x,
                    dy: -y,
                    scale: size
                }, this.forEach(function (el) {
                    el.transform("...")
                }), this
            };
            var createNode;
            R._engine.initWin = function (win) {
                var doc = win.document;
                doc.createStyleSheet().addRule(".rvml", "behavior:url(#default#VML)");
                try {
                    !doc.namespaces.rvml && doc.namespaces.add("rvml", "urn:schemas-microsoft-com:vml"), createNode = function (tagName) {
                        return doc.createElement("<rvml:" + tagName + ' class="rvml">')
                    }
                } catch (e) {
                    createNode = function (tagName) {
                        return doc.createElement("<" + tagName + ' xmlns="urn:schemas-microsoft.com:vml" class="rvml">')
                    }
                }
            }, R._engine.initWin(R._g.win), R._engine.create = function () {
                var con = R._getContainer.apply(0, arguments), container = con.container, height = con.height,
                    width = con.width, x = con.x, y = con.y;
                if (!container) throw new Error("VML container not found.");
                var res = new R._Paper, c = res.canvas = R._g.doc.createElement("div"), cs = c.style;
                return x = x || 0, y = y || 0, width = width || 512, height = height || 342, res.width = width, res.height = height, width == +width && (width += "px"), height == +height && (height += "px"), res.coordsize = 1e3 * zoom + S + 1e3 * zoom, res.coordorigin = "0 0", res.span = R._g.doc.createElement("span"), res.span.style.cssText = "position:absolute;left:-9999em;top:-9999em;padding:0;margin:0;line-height:1;", c.appendChild(res.span), cs.cssText = R.format("top:0;left:0;width:{0};height:{1};display:inline-block;position:relative;clip:rect(0 {0} {1} 0);overflow:hidden", width, height), 1 == container ? (R._g.doc.body.appendChild(c), cs.left = x + "px", cs.top = y + "px", cs.position = "absolute") : container.firstChild ? container.insertBefore(c, container.firstChild) : container.appendChild(c), res.renderfix = function () {
                }, res
            }, R.prototype.clear = function () {
                R.eve("raphael.clear", this), this.canvas.innerHTML = E, this.span = R._g.doc.createElement("span"), this.span.style.cssText = "position:absolute;left:-9999em;top:-9999em;padding:0;margin:0;line-height:1;display:inline;", this.canvas.appendChild(this.span), this.bottom = this.top = null
            }, R.prototype.remove = function () {
                R.eve("raphael.remove", this), this.canvas.parentNode.removeChild(this.canvas);
                for (var i in this) this[i] = "function" == typeof this[i] ? R._removedFactory(i) : null;
                return !0
            };
            var setproto = R.st;
            for (var method in elproto) elproto[has](method) && !setproto[has](method) && (setproto[method] = function (methodname) {
                return function () {
                    var arg = arguments;
                    return this.forEach(function (el) {
                        el[methodname].apply(el, arg)
                    })
                }
            }(method))
        }
    }(), oldRaphael.was ? g.win.Raphael = R : Raphael = R, R
});