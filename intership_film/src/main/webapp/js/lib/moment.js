(function (undefined) {
    function defaultParsingFlags() {
        return {
            empty: !1,
            unusedTokens: [],
            unusedInput: [],
            overflow: -2,
            charsLeftOver: 0,
            nullInput: !1,
            invalidMonth: null,
            invalidFormat: !1,
            userInvalidated: !1,
            iso: !1
        }
    }

    function padToken(func, count) {
        return function (a) {
            return leftZeroFill(func.call(this, a), count)
        }
    }

    function ordinalizeToken(func, period) {
        return function (a) {
            return this.lang().ordinal(func.call(this, a), period)
        }
    }

    function Language() {
    }

    function Moment(config) {
        checkOverflow(config), extend(this, config)
    }

    function Duration(duration) {
        var normalizedInput = normalizeObjectUnits(duration), years = normalizedInput.year || 0,
            months = normalizedInput.month || 0, weeks = normalizedInput.week || 0, days = normalizedInput.day || 0,
            hours = normalizedInput.hour || 0, minutes = normalizedInput.minute || 0,
            seconds = normalizedInput.second || 0, milliseconds = normalizedInput.millisecond || 0;
        this._milliseconds = +milliseconds + 1e3 * seconds + 6e4 * minutes + 36e5 * hours, this._days = +days + 7 * weeks, this._months = +months + 12 * years, this._data = {}, this._bubble()
    }

    function extend(a, b) {
        for (var i in b) b.hasOwnProperty(i) && (a[i] = b[i]);
        return b.hasOwnProperty("toString") && (a.toString = b.toString), b.hasOwnProperty("valueOf") && (a.valueOf = b.valueOf), a
    }

    function cloneMoment(m) {
        var i, result = {};
        for (i in m) m.hasOwnProperty(i) && momentProperties.hasOwnProperty(i) && (result[i] = m[i]);
        return result
    }

    function absRound(number) {
        return 0 > number ? Math.ceil(number) : Math.floor(number)
    }

    function leftZeroFill(number, targetLength, forceSign) {
        for (var output = "" + Math.abs(number), sign = number >= 0; output.length < targetLength;) output = "0" + output;
        return (sign ? forceSign ? "+" : "" : "-") + output
    }

    function addOrSubtractDurationFromMoment(mom, duration, isAdding, ignoreUpdateOffset) {
        var minutes, hours, milliseconds = duration._milliseconds, days = duration._days, months = duration._months;
        milliseconds && mom._d.setTime(+mom._d + milliseconds * isAdding), (days || months) && (minutes = mom.minute(), hours = mom.hour()), days && mom.date(mom.date() + days * isAdding), months && mom.month(mom.month() + months * isAdding), milliseconds && !ignoreUpdateOffset && moment.updateOffset(mom), (days || months) && (mom.minute(minutes), mom.hour(hours))
    }

    function isArray(input) {
        return "[object Array]" === Object.prototype.toString.call(input)
    }

    function isDate(input) {
        return "[object Date]" === Object.prototype.toString.call(input) || input instanceof Date
    }

    function compareArrays(array1, array2, dontConvert) {
        var i, len = Math.min(array1.length, array2.length), lengthDiff = Math.abs(array1.length - array2.length),
            diffs = 0;
        for (i = 0; len > i; i++) (dontConvert && array1[i] !== array2[i] || !dontConvert && toInt(array1[i]) !== toInt(array2[i])) && diffs++;
        return diffs + lengthDiff
    }

    function normalizeUnits(units) {
        if (units) {
            var lowered = units.toLowerCase().replace(/(.)s$/, "$1");
            units = unitAliases[units] || camelFunctions[lowered] || lowered
        }
        return units
    }

    function normalizeObjectUnits(inputObject) {
        var normalizedProp, prop, normalizedInput = {};
        for (prop in inputObject) inputObject.hasOwnProperty(prop) && (normalizedProp = normalizeUnits(prop), normalizedProp && (normalizedInput[normalizedProp] = inputObject[prop]));
        return normalizedInput
    }

    function makeList(field) {
        var count, setter;
        if (0 === field.indexOf("week")) count = 7, setter = "day"; else {
            if (0 !== field.indexOf("month")) return;
            count = 12, setter = "month"
        }
        moment[field] = function (format, index) {
            var i, getter, method = moment.fn._lang[field], results = [];
            if ("number" == typeof format && (index = format, format = undefined), getter = function (i) {
                var m = moment().utc().set(setter, i);
                return method.call(moment.fn._lang, m, format || "")
            }, null != index) return getter(index);
            for (i = 0; count > i; i++) results.push(getter(i));
            return results
        }
    }

    function toInt(argumentForCoercion) {
        var coercedNumber = +argumentForCoercion, value = 0;
        return 0 !== coercedNumber && isFinite(coercedNumber) && (value = coercedNumber >= 0 ? Math.floor(coercedNumber) : Math.ceil(coercedNumber)), value
    }

    function daysInMonth(year, month) {
        return new Date(Date.UTC(year, month + 1, 0)).getUTCDate()
    }

    function daysInYear(year) {
        return isLeapYear(year) ? 366 : 365
    }

    function isLeapYear(year) {
        return year % 4 === 0 && year % 100 !== 0 || year % 400 === 0
    }

    function checkOverflow(m) {
        var overflow;
        m._a && -2 === m._pf.overflow && (overflow = m._a[MONTH] < 0 || m._a[MONTH] > 11 ? MONTH : m._a[DATE] < 1 || m._a[DATE] > daysInMonth(m._a[YEAR], m._a[MONTH]) ? DATE : m._a[HOUR] < 0 || m._a[HOUR] > 23 ? HOUR : m._a[MINUTE] < 0 || m._a[MINUTE] > 59 ? MINUTE : m._a[SECOND] < 0 || m._a[SECOND] > 59 ? SECOND : m._a[MILLISECOND] < 0 || m._a[MILLISECOND] > 999 ? MILLISECOND : -1, m._pf._overflowDayOfYear && (YEAR > overflow || overflow > DATE) && (overflow = DATE), m._pf.overflow = overflow)
    }

    function isValid(m) {
        return null == m._isValid && (m._isValid = !isNaN(m._d.getTime()) && m._pf.overflow < 0 && !m._pf.empty && !m._pf.invalidMonth && !m._pf.nullInput && !m._pf.invalidFormat && !m._pf.userInvalidated, m._strict && (m._isValid = m._isValid && 0 === m._pf.charsLeftOver && 0 === m._pf.unusedTokens.length)), m._isValid
    }

    function normalizeLanguage(key) {
        return key ? key.toLowerCase().replace("_", "-") : key
    }

    function makeAs(input, model) {
        return model._isUTC ? moment(input).zone(model._offset || 0) : moment(input).local()
    }

    function loadLang(key, values) {
        return values.abbr = key, languages[key] || (languages[key] = new Language), languages[key].set(values), languages[key]
    }

    function unloadLang(key) {
        delete languages[key]
    }

    function getLangDefinition(key) {
        var j, lang, next, split, i = 0, get = function (k) {
            if (!languages[k] && hasModule) try {
                require("./lang/" + k)
            } catch (e) {
            }
            return languages[k]
        };
        if (!key) return moment.fn._lang;
        if (!isArray(key)) {
            if (lang = get(key)) return lang;
            key = [key]
        }
        for (; i < key.length;) {
            for (split = normalizeLanguage(key[i]).split("-"), j = split.length, next = normalizeLanguage(key[i + 1]), next = next ? next.split("-") : null; j > 0;) {
                if (lang = get(split.slice(0, j).join("-"))) return lang;
                if (next && next.length >= j && compareArrays(split, next, !0) >= j - 1) break;
                j--
            }
            i++
        }
        return moment.fn._lang
    }

    function removeFormattingTokens(input) {
        return input.match(/\[[\s\S]/) ? input.replace(/^\[|\]$/g, "") : input.replace(/\\/g, "")
    }

    function makeFormatFunction(format) {
        var i, length, array = format.match(formattingTokens);
        for (i = 0, length = array.length; length > i; i++) array[i] = formatTokenFunctions[array[i]] ? formatTokenFunctions[array[i]] : removeFormattingTokens(array[i]);
        return function (mom) {
            var output = "";
            for (i = 0; length > i; i++) output += array[i] instanceof Function ? array[i].call(mom, format) : array[i];
            return output
        }
    }

    function formatMoment(m, format) {
        return m.isValid() ? (format = expandFormat(format, m.lang()), formatFunctions[format] || (formatFunctions[format] = makeFormatFunction(format)), formatFunctions[format](m)) : m.lang().invalidDate()
    }

    function expandFormat(format, lang) {
        function replaceLongDateFormatTokens(input) {
            return lang.longDateFormat(input) || input
        }

        var i = 5;
        for (localFormattingTokens.lastIndex = 0; i >= 0 && localFormattingTokens.test(format);) format = format.replace(localFormattingTokens, replaceLongDateFormatTokens), localFormattingTokens.lastIndex = 0, i -= 1;
        return format
    }

    function getParseRegexForToken(token, config) {
        var a, strict = config._strict;
        switch (token) {
            case"DDDD":
                return parseTokenThreeDigits;
            case"YYYY":
            case"GGGG":
            case"gggg":
                return strict ? parseTokenFourDigits : parseTokenOneToFourDigits;
            case"Y":
            case"G":
            case"g":
                return parseTokenSignedNumber;
            case"YYYYYY":
            case"YYYYY":
            case"GGGGG":
            case"ggggg":
                return strict ? parseTokenSixDigits : parseTokenOneToSixDigits;
            case"S":
                if (strict) return parseTokenOneDigit;
            case"SS":
                if (strict) return parseTokenTwoDigits;
            case"SSS":
                if (strict) return parseTokenThreeDigits;
            case"DDD":
                return parseTokenOneToThreeDigits;
            case"MMM":
            case"MMMM":
            case"dd":
            case"ddd":
            case"dddd":
                return parseTokenWord;
            case"a":
            case"A":
                return getLangDefinition(config._l)._meridiemParse;
            case"X":
                return parseTokenTimestampMs;
            case"Z":
            case"ZZ":
                return parseTokenTimezone;
            case"T":
                return parseTokenT;
            case"SSSS":
                return parseTokenDigits;
            case"MM":
            case"DD":
            case"YY":
            case"GG":
            case"gg":
            case"HH":
            case"hh":
            case"mm":
            case"ss":
            case"ww":
            case"WW":
                return strict ? parseTokenTwoDigits : parseTokenOneOrTwoDigits;
            case"M":
            case"D":
            case"d":
            case"H":
            case"h":
            case"m":
            case"s":
            case"w":
            case"W":
            case"e":
            case"E":
                return parseTokenOneOrTwoDigits;
            default:
                return a = new RegExp(regexpEscape(unescapeFormat(token.replace("\\", "")), "i"))
        }
    }

    function timezoneMinutesFromString(string) {
        string = string || "";
        var possibleTzMatches = string.match(parseTokenTimezone) || [],
            tzChunk = possibleTzMatches[possibleTzMatches.length - 1] || [],
            parts = (tzChunk + "").match(parseTimezoneChunker) || ["-", 0, 0],
            minutes = +(60 * parts[1]) + toInt(parts[2]);
        return "+" === parts[0] ? -minutes : minutes
    }

    function addTimeToArrayFromToken(token, input, config) {
        var a, datePartArray = config._a;
        switch (token) {
            case"M":
            case"MM":
                null != input && (datePartArray[MONTH] = toInt(input) - 1);
                break;
            case"MMM":
            case"MMMM":
                a = getLangDefinition(config._l).monthsParse(input), null != a ? datePartArray[MONTH] = a : config._pf.invalidMonth = input;
                break;
            case"D":
            case"DD":
                null != input && (datePartArray[DATE] = toInt(input));
                break;
            case"DDD":
            case"DDDD":
                null != input && (config._dayOfYear = toInt(input));
                break;
            case"YY":
                datePartArray[YEAR] = toInt(input) + (toInt(input) > 68 ? 1900 : 2e3);
                break;
            case"YYYY":
            case"YYYYY":
            case"YYYYYY":
                datePartArray[YEAR] = toInt(input);
                break;
            case"a":
            case"A":
                config._isPm = getLangDefinition(config._l).isPM(input);
                break;
            case"H":
            case"HH":
            case"h":
            case"hh":
                datePartArray[HOUR] = toInt(input);
                break;
            case"m":
            case"mm":
                datePartArray[MINUTE] = toInt(input);
                break;
            case"s":
            case"ss":
                datePartArray[SECOND] = toInt(input);
                break;
            case"S":
            case"SS":
            case"SSS":
            case"SSSS":
                datePartArray[MILLISECOND] = toInt(1e3 * ("0." + input));
                break;
            case"X":
                config._d = new Date(1e3 * parseFloat(input));
                break;
            case"Z":
            case"ZZ":
                config._useUTC = !0, config._tzm = timezoneMinutesFromString(input);
                break;
            case"w":
            case"ww":
            case"W":
            case"WW":
            case"d":
            case"dd":
            case"ddd":
            case"dddd":
            case"e":
            case"E":
                token = token.substr(0, 1);
            case"gg":
            case"gggg":
            case"GG":
            case"GGGG":
            case"GGGGG":
                token = token.substr(0, 2), input && (config._w = config._w || {}, config._w[token] = input)
        }
    }

    function dateFromConfig(config) {
        var i, date, currentDate, yearToUse, fixYear, w, temp, lang, weekday, week, input = [];
        if (!config._d) {
            for (currentDate = currentDateArray(config), config._w && null == config._a[DATE] && null == config._a[MONTH] && (fixYear = function (val) {
                var int_val = parseInt(val, 10);
                return val ? val.length < 3 ? int_val > 68 ? 1900 + int_val : 2e3 + int_val : int_val : null == config._a[YEAR] ? moment().weekYear() : config._a[YEAR]
            }, w = config._w, null != w.GG || null != w.W || null != w.E ? temp = dayOfYearFromWeeks(fixYear(w.GG), w.W || 1, w.E, 4, 1) : (lang = getLangDefinition(config._l), weekday = null != w.d ? parseWeekday(w.d, lang) : null != w.e ? parseInt(w.e, 10) + lang._week.dow : 0, week = parseInt(w.w, 10) || 1, null != w.d && weekday < lang._week.dow && week++, temp = dayOfYearFromWeeks(fixYear(w.gg), week, weekday, lang._week.doy, lang._week.dow)), config._a[YEAR] = temp.year, config._dayOfYear = temp.dayOfYear), config._dayOfYear && (yearToUse = null == config._a[YEAR] ? currentDate[YEAR] : config._a[YEAR], config._dayOfYear > daysInYear(yearToUse) && (config._pf._overflowDayOfYear = !0), date = makeUTCDate(yearToUse, 0, config._dayOfYear), config._a[MONTH] = date.getUTCMonth(), config._a[DATE] = date.getUTCDate()), i = 0; 3 > i && null == config._a[i]; ++i) config._a[i] = input[i] = currentDate[i];
            for (; 7 > i; i++) config._a[i] = input[i] = null == config._a[i] ? 2 === i ? 1 : 0 : config._a[i];
            input[HOUR] += toInt((config._tzm || 0) / 60), input[MINUTE] += toInt((config._tzm || 0) % 60), config._d = (config._useUTC ? makeUTCDate : makeDate).apply(null, input)
        }
    }

    function dateFromObject(config) {
        var normalizedInput;
        config._d || (normalizedInput = normalizeObjectUnits(config._i), config._a = [normalizedInput.year, normalizedInput.month, normalizedInput.day, normalizedInput.hour, normalizedInput.minute, normalizedInput.second, normalizedInput.millisecond], dateFromConfig(config))
    }

    function currentDateArray(config) {
        var now = new Date;
        return config._useUTC ? [now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate()] : [now.getFullYear(), now.getMonth(), now.getDate()]
    }

    function makeDateFromStringAndFormat(config) {
        config._a = [], config._pf.empty = !0;
        var i, parsedInput, tokens, token, skipped, lang = getLangDefinition(config._l), string = "" + config._i,
            stringLength = string.length, totalParsedInputLength = 0;
        for (tokens = expandFormat(config._f, lang).match(formattingTokens) || [], i = 0; i < tokens.length; i++) token = tokens[i], parsedInput = (string.match(getParseRegexForToken(token, config)) || [])[0], parsedInput && (skipped = string.substr(0, string.indexOf(parsedInput)), skipped.length > 0 && config._pf.unusedInput.push(skipped), string = string.slice(string.indexOf(parsedInput) + parsedInput.length), totalParsedInputLength += parsedInput.length), formatTokenFunctions[token] ? (parsedInput ? config._pf.empty = !1 : config._pf.unusedTokens.push(token), addTimeToArrayFromToken(token, parsedInput, config)) : config._strict && !parsedInput && config._pf.unusedTokens.push(token);
        config._pf.charsLeftOver = stringLength - totalParsedInputLength, string.length > 0 && config._pf.unusedInput.push(string), config._isPm && config._a[HOUR] < 12 && (config._a[HOUR] += 12), config._isPm === !1 && 12 === config._a[HOUR] && (config._a[HOUR] = 0), dateFromConfig(config), checkOverflow(config)
    }

    function unescapeFormat(s) {
        return s.replace(/\\(\[)|\\(\])|\[([^\]\[]*)\]|\\(.)/g, function (matched, p1, p2, p3, p4) {
            return p1 || p2 || p3 || p4
        })
    }

    function regexpEscape(s) {
        return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&")
    }

    function makeDateFromStringAndArray(config) {
        var tempConfig, bestMoment, scoreToBeat, i, currentScore;
        if (0 === config._f.length) return config._pf.invalidFormat = !0, config._d = new Date(0 / 0), void 0;
        for (i = 0; i < config._f.length; i++) currentScore = 0, tempConfig = extend({}, config), tempConfig._pf = defaultParsingFlags(), tempConfig._f = config._f[i], makeDateFromStringAndFormat(tempConfig), isValid(tempConfig) && (currentScore += tempConfig._pf.charsLeftOver, currentScore += 10 * tempConfig._pf.unusedTokens.length, tempConfig._pf.score = currentScore, (null == scoreToBeat || scoreToBeat > currentScore) && (scoreToBeat = currentScore, bestMoment = tempConfig));
        extend(config, bestMoment || tempConfig)
    }

    function makeDateFromString(config) {
        var i, l, string = config._i, match = isoRegex.exec(string);
        if (match) {
            for (config._pf.iso = !0, i = 0, l = isoDates.length; l > i; i++) if (isoDates[i][1].exec(string)) {
                config._f = isoDates[i][0] + (match[6] || " ");
                break
            }
            for (i = 0, l = isoTimes.length; l > i; i++) if (isoTimes[i][1].exec(string)) {
                config._f += isoTimes[i][0];
                break
            }
            string.match(parseTokenTimezone) && (config._f += "Z"), makeDateFromStringAndFormat(config)
        } else config._d = new Date(string)
    }

    function makeDateFromInput(config) {
        var input = config._i, matched = aspNetJsonRegex.exec(input);
        input === undefined ? config._d = new Date : matched ? config._d = new Date(+matched[1]) : "string" == typeof input ? makeDateFromString(config) : isArray(input) ? (config._a = input.slice(0), dateFromConfig(config)) : isDate(input) ? config._d = new Date(+input) : "object" == typeof input ? dateFromObject(config) : config._d = new Date(input)
    }

    function makeDate(y, m, d, h, M, s, ms) {
        var date = new Date(y, m, d, h, M, s, ms);
        return 1970 > y && date.setFullYear(y), date
    }

    function makeUTCDate(y) {
        var date = new Date(Date.UTC.apply(null, arguments));
        return 1970 > y && date.setUTCFullYear(y), date
    }

    function parseWeekday(input, language) {
        if ("string" == typeof input) if (isNaN(input)) {
            if (input = language.weekdaysParse(input), "number" != typeof input) return null
        } else input = parseInt(input, 10);
        return input
    }

    function substituteTimeAgo(string, number, withoutSuffix, isFuture, lang) {
        return lang.relativeTime(number || 1, !!withoutSuffix, string, isFuture)
    }

    function relativeTime(milliseconds, withoutSuffix, lang) {
        var seconds = round(Math.abs(milliseconds) / 1e3), minutes = round(seconds / 60), hours = round(minutes / 60),
            days = round(hours / 24), years = round(days / 365),
            args = 45 > seconds && ["s", seconds] || 1 === minutes && ["m"] || 45 > minutes && ["mm", minutes] || 1 === hours && ["h"] || 22 > hours && ["hh", hours] || 1 === days && ["d"] || 25 >= days && ["dd", days] || 45 >= days && ["M"] || 345 > days && ["MM", round(days / 30)] || 1 === years && ["y"] || ["yy", years];
        return args[2] = withoutSuffix, args[3] = milliseconds > 0, args[4] = lang, substituteTimeAgo.apply({}, args)
    }

    function weekOfYear(mom, firstDayOfWeek, firstDayOfWeekOfYear) {
        var adjustedMoment, end = firstDayOfWeekOfYear - firstDayOfWeek,
            daysToDayOfWeek = firstDayOfWeekOfYear - mom.day();
        return daysToDayOfWeek > end && (daysToDayOfWeek -= 7), end - 7 > daysToDayOfWeek && (daysToDayOfWeek += 7), adjustedMoment = moment(mom).add("d", daysToDayOfWeek), {
            week: Math.ceil(adjustedMoment.dayOfYear() / 7),
            year: adjustedMoment.year()
        }
    }

    function dayOfYearFromWeeks(year, week, weekday, firstDayOfWeekOfYear, firstDayOfWeek) {
        var daysToAdd, dayOfYear, d = makeUTCDate(year, 0, 1).getUTCDay();
        return weekday = null != weekday ? weekday : firstDayOfWeek, daysToAdd = firstDayOfWeek - d + (d > firstDayOfWeekOfYear ? 7 : 0) - (firstDayOfWeek > d ? 7 : 0), dayOfYear = 7 * (week - 1) + (weekday - firstDayOfWeek) + daysToAdd + 1, {
            year: dayOfYear > 0 ? year : year - 1,
            dayOfYear: dayOfYear > 0 ? dayOfYear : daysInYear(year - 1) + dayOfYear
        }
    }

    function makeMoment(config) {
        var input = config._i, format = config._f;
        return null === input ? moment.invalid({nullInput: !0}) : ("string" == typeof input && (config._i = input = getLangDefinition().preparse(input)), moment.isMoment(input) ? (config = cloneMoment(input), config._d = new Date(+input._d)) : format ? isArray(format) ? makeDateFromStringAndArray(config) : makeDateFromStringAndFormat(config) : makeDateFromInput(config), new Moment(config))
    }

    function makeGetterAndSetter(name, key) {
        moment.fn[name] = moment.fn[name + "s"] = function (input) {
            var utc = this._isUTC ? "UTC" : "";
            return null != input ? (this._d["set" + utc + key](input), moment.updateOffset(this), this) : this._d["get" + utc + key]()
        }
    }

    function makeDurationGetter(name) {
        moment.duration.fn[name] = function () {
            return this._data[name]
        }
    }

    function makeDurationAsGetter(name, factor) {
        moment.duration.fn["as" + name] = function () {
            return +this / factor
        }
    }

    function makeGlobal(deprecate) {
        var warned = !1, local_moment = moment;
        "undefined" == typeof ender && (deprecate ? (global.moment = function () {
            return !warned && console && console.warn && (warned = !0, console.warn("Accessing Moment through the global scope is deprecated, and will be removed in an upcoming release.")), local_moment.apply(null, arguments)
        }, extend(global.moment, local_moment)) : global.moment = moment)
    }

    for (var moment, i, VERSION = "2.5.0", global = this, round = Math.round, YEAR = 0, MONTH = 1, DATE = 2, HOUR = 3, MINUTE = 4, SECOND = 5, MILLISECOND = 6, languages = {}, momentProperties = {
        _isAMomentObject: null,
        _i: null,
        _f: null,
        _l: null,
        _strict: null,
        _isUTC: null,
        _offset: null,
        _pf: null,
        _lang: null
    }, hasModule = "undefined" != typeof module && module.exports && "undefined" != typeof require, aspNetJsonRegex = /^\/?Date\((\-?\d+)/i, aspNetTimeSpanJsonRegex = /(\-)?(?:(\d*)\.)?(\d+)\:(\d+)(?:\:(\d+)\.?(\d{3})?)?/, isoDurationRegex = /^(-)?P(?:(?:([0-9,.]*)Y)?(?:([0-9,.]*)M)?(?:([0-9,.]*)D)?(?:T(?:([0-9,.]*)H)?(?:([0-9,.]*)M)?(?:([0-9,.]*)S)?)?|([0-9,.]*)W)$/, formattingTokens = /(\[[^\[]*\])|(\\)?(Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|YYYYYY|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|mm?|ss?|S{1,4}|X|zz?|ZZ?|.)/g, localFormattingTokens = /(\[[^\[]*\])|(\\)?(LT|LL?L?L?|l{1,4})/g, parseTokenOneOrTwoDigits = /\d\d?/, parseTokenOneToThreeDigits = /\d{1,3}/, parseTokenOneToFourDigits = /\d{1,4}/, parseTokenOneToSixDigits = /[+\-]?\d{1,6}/, parseTokenDigits = /\d+/, parseTokenWord = /[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i, parseTokenTimezone = /Z|[\+\-]\d\d:?\d\d/gi, parseTokenT = /T/i, parseTokenTimestampMs = /[\+\-]?\d+(\.\d{1,3})?/, parseTokenOneDigit = /\d/, parseTokenTwoDigits = /\d\d/, parseTokenThreeDigits = /\d{3}/, parseTokenFourDigits = /\d{4}/, parseTokenSixDigits = /[+-]?\d{6}/, parseTokenSignedNumber = /[+-]?\d+/, isoRegex = /^\s*(?:[+-]\d{6}|\d{4})-(?:(\d\d-\d\d)|(W\d\d$)|(W\d\d-\d)|(\d\d\d))((T| )(\d\d(:\d\d(:\d\d(\.\d+)?)?)?)?([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/, isoFormat = "YYYY-MM-DDTHH:mm:ssZ", isoDates = [["YYYYYY-MM-DD", /[+-]\d{6}-\d{2}-\d{2}/], ["YYYY-MM-DD", /\d{4}-\d{2}-\d{2}/], ["GGGG-[W]WW-E", /\d{4}-W\d{2}-\d/], ["GGGG-[W]WW", /\d{4}-W\d{2}/], ["YYYY-DDD", /\d{4}-\d{3}/]], isoTimes = [["HH:mm:ss.SSSS", /(T| )\d\d:\d\d:\d\d\.\d{1,3}/], ["HH:mm:ss", /(T| )\d\d:\d\d:\d\d/], ["HH:mm", /(T| )\d\d:\d\d/], ["HH", /(T| )\d\d/]], parseTimezoneChunker = /([\+\-]|\d\d)/gi, proxyGettersAndSetters = "Date|Hours|Minutes|Seconds|Milliseconds".split("|"), unitMillisecondFactors = {
        Milliseconds: 1,
        Seconds: 1e3,
        Minutes: 6e4,
        Hours: 36e5,
        Days: 864e5,
        Months: 2592e6,
        Years: 31536e6
    }, unitAliases = {
        ms: "millisecond",
        s: "second",
        m: "minute",
        h: "hour",
        d: "day",
        D: "date",
        w: "week",
        W: "isoWeek",
        M: "month",
        y: "year",
        DDD: "dayOfYear",
        e: "weekday",
        E: "isoWeekday",
        gg: "weekYear",
        GG: "isoWeekYear"
    }, camelFunctions = {
        dayofyear: "dayOfYear",
        isoweekday: "isoWeekday",
        isoweek: "isoWeek",
        weekyear: "weekYear",
        isoweekyear: "isoWeekYear"
    }, formatFunctions = {}, ordinalizeTokens = "DDD w W M D d".split(" "), paddedTokens = "M D H h m s w W".split(" "), formatTokenFunctions = {
        M: function () {
            return this.month() + 1
        }, MMM: function (format) {
            return this.lang().monthsShort(this, format)
        }, MMMM: function (format) {
            return this.lang().months(this, format)
        }, D: function () {
            return this.date()
        }, DDD: function () {
            return this.dayOfYear()
        }, d: function () {
            return this.day()
        }, dd: function (format) {
            return this.lang().weekdaysMin(this, format)
        }, ddd: function (format) {
            return this.lang().weekdaysShort(this, format)
        }, dddd: function (format) {
            return this.lang().weekdays(this, format)
        }, w: function () {
            return this.week()
        }, W: function () {
            return this.isoWeek()
        }, YY: function () {
            return leftZeroFill(this.year() % 100, 2)
        }, YYYY: function () {
            return leftZeroFill(this.year(), 4)
        }, YYYYY: function () {
            return leftZeroFill(this.year(), 5)
        }, YYYYYY: function () {
            var y = this.year(), sign = y >= 0 ? "+" : "-";
            return sign + leftZeroFill(Math.abs(y), 6)
        }, gg: function () {
            return leftZeroFill(this.weekYear() % 100, 2)
        }, gggg: function () {
            return leftZeroFill(this.weekYear(), 4)
        }, ggggg: function () {
            return leftZeroFill(this.weekYear(), 5)
        }, GG: function () {
            return leftZeroFill(this.isoWeekYear() % 100, 2)
        }, GGGG: function () {
            return leftZeroFill(this.isoWeekYear(), 4)
        }, GGGGG: function () {
            return leftZeroFill(this.isoWeekYear(), 5)
        }, e: function () {
            return this.weekday()
        }, E: function () {
            return this.isoWeekday()
        }, a: function () {
            return this.lang().meridiem(this.hours(), this.minutes(), !0)
        }, A: function () {
            return this.lang().meridiem(this.hours(), this.minutes(), !1)
        }, H: function () {
            return this.hours()
        }, h: function () {
            return this.hours() % 12 || 12
        }, m: function () {
            return this.minutes()
        }, s: function () {
            return this.seconds()
        }, S: function () {
            return toInt(this.milliseconds() / 100)
        }, SS: function () {
            return leftZeroFill(toInt(this.milliseconds() / 10), 2)
        }, SSS: function () {
            return leftZeroFill(this.milliseconds(), 3)
        }, SSSS: function () {
            return leftZeroFill(this.milliseconds(), 3)
        }, Z: function () {
            var a = -this.zone(), b = "+";
            return 0 > a && (a = -a, b = "-"), b + leftZeroFill(toInt(a / 60), 2) + ":" + leftZeroFill(toInt(a) % 60, 2)
        }, ZZ: function () {
            var a = -this.zone(), b = "+";
            return 0 > a && (a = -a, b = "-"), b + leftZeroFill(toInt(a / 60), 2) + leftZeroFill(toInt(a) % 60, 2)
        }, z: function () {
            return this.zoneAbbr()
        }, zz: function () {
            return this.zoneName()
        }, X: function () {
            return this.unix()
        }, Q: function () {
            return this.quarter()
        }
    }, lists = ["months", "monthsShort", "weekdays", "weekdaysShort", "weekdaysMin"]; ordinalizeTokens.length;) i = ordinalizeTokens.pop(), formatTokenFunctions[i + "o"] = ordinalizeToken(formatTokenFunctions[i], i);
    for (; paddedTokens.length;) i = paddedTokens.pop(), formatTokenFunctions[i + i] = padToken(formatTokenFunctions[i], 2);
    for (formatTokenFunctions.DDDD = padToken(formatTokenFunctions.DDD, 3), extend(Language.prototype, {
        set: function (config) {
            var prop, i;
            for (i in config) prop = config[i], "function" == typeof prop ? this[i] = prop : this["_" + i] = prop
        },
        _months: "January_February_March_April_May_June_July_August_September_October_November_December".split("_"),
        months: function (m) {
            return this._months[m.month()]
        },
        _monthsShort: "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),
        monthsShort: function (m) {
            return this._monthsShort[m.month()]
        },
        monthsParse: function (monthName) {
            var i, mom, regex;
            for (this._monthsParse || (this._monthsParse = []), i = 0; 12 > i; i++) if (this._monthsParse[i] || (mom = moment.utc([2e3, i]), regex = "^" + this.months(mom, "") + "|^" + this.monthsShort(mom, ""), this._monthsParse[i] = new RegExp(regex.replace(".", ""), "i")), this._monthsParse[i].test(monthName)) return i
        },
        _weekdays: "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),
        weekdays: function (m) {
            return this._weekdays[m.day()]
        },
        _weekdaysShort: "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),
        weekdaysShort: function (m) {
            return this._weekdaysShort[m.day()]
        },
        _weekdaysMin: "Su_Mo_Tu_We_Th_Fr_Sa".split("_"),
        weekdaysMin: function (m) {
            return this._weekdaysMin[m.day()]
        },
        weekdaysParse: function (weekdayName) {
            var i, mom, regex;
            for (this._weekdaysParse || (this._weekdaysParse = []), i = 0; 7 > i; i++) if (this._weekdaysParse[i] || (mom = moment([2e3, 1]).day(i), regex = "^" + this.weekdays(mom, "") + "|^" + this.weekdaysShort(mom, "") + "|^" + this.weekdaysMin(mom, ""), this._weekdaysParse[i] = new RegExp(regex.replace(".", ""), "i")), this._weekdaysParse[i].test(weekdayName)) return i
        },
        _longDateFormat: {
            LT: "h:mm A",
            L: "MM/DD/YYYY",
            LL: "MMMM D YYYY",
            LLL: "MMMM D YYYY LT",
            LLLL: "dddd, MMMM D YYYY LT"
        },
        longDateFormat: function (key) {
            var output = this._longDateFormat[key];
            return !output && this._longDateFormat[key.toUpperCase()] && (output = this._longDateFormat[key.toUpperCase()].replace(/MMMM|MM|DD|dddd/g, function (val) {
                return val.slice(1)
            }), this._longDateFormat[key] = output), output
        },
        isPM: function (input) {
            return "p" === (input + "").toLowerCase().charAt(0)
        },
        _meridiemParse: /[ap]\.?m?\.?/i,
        meridiem: function (hours, minutes, isLower) {
            return hours > 11 ? isLower ? "pm" : "PM" : isLower ? "am" : "AM"
        },
        _calendar: {
            sameDay: "[Today at] LT",
            nextDay: "[Tomorrow at] LT",
            nextWeek: "dddd [at] LT",
            lastDay: "[Yesterday at] LT",
            lastWeek: "[Last] dddd [at] LT",
            sameElse: "L"
        },
        calendar: function (key, mom) {
            var output = this._calendar[key];
            return "function" == typeof output ? output.apply(mom) : output
        },
        _relativeTime: {
            future: "in %s",
            past: "%s ago",
            s: "a few seconds",
            m: "a minute",
            mm: "%d minutes",
            h: "an hour",
            hh: "%d hours",
            d: "a day",
            dd: "%d days",
            M: "a month",
            MM: "%d months",
            y: "a year",
            yy: "%d years"
        },
        relativeTime: function (number, withoutSuffix, string, isFuture) {
            var output = this._relativeTime[string];
            return "function" == typeof output ? output(number, withoutSuffix, string, isFuture) : output.replace(/%d/i, number)
        },
        pastFuture: function (diff, output) {
            var format = this._relativeTime[diff > 0 ? "future" : "past"];
            return "function" == typeof format ? format(output) : format.replace(/%s/i, output)
        },
        ordinal: function (number) {
            return this._ordinal.replace("%d", number)
        },
        _ordinal: "%d",
        preparse: function (string) {
            return string
        },
        postformat: function (string) {
            return string
        },
        week: function (mom) {
            return weekOfYear(mom, this._week.dow, this._week.doy).week
        },
        _week: {dow: 0, doy: 6},
        _invalidDate: "Invalid date",
        invalidDate: function () {
            return this._invalidDate
        }
    }), moment = function (input, format, lang, strict) {
        return "boolean" == typeof lang && (strict = lang, lang = undefined), makeMoment({
            _isAMomentObject: !0,
            _i: input,
            _f: format,
            _l: lang,
            _strict: strict,
            _isUTC: !1,
            _pf: defaultParsingFlags()
        })
    }, moment.utc = function (input, format, lang, strict) {
        var m;
        return "boolean" == typeof lang && (strict = lang, lang = undefined), m = makeMoment({
            _isAMomentObject: !0,
            _useUTC: !0,
            _isUTC: !0,
            _l: lang,
            _i: input,
            _f: format,
            _strict: strict,
            _pf: defaultParsingFlags()
        }).utc()
    }, moment.unix = function (input) {
        return moment(1e3 * input)
    }, moment.duration = function (input, key) {
        var sign, ret, parseIso, duration = input, match = null;
        return moment.isDuration(input) ? duration = {
            ms: input._milliseconds,
            d: input._days,
            M: input._months
        } : "number" == typeof input ? (duration = {}, key ? duration[key] = input : duration.milliseconds = input) : (match = aspNetTimeSpanJsonRegex.exec(input)) ? (sign = "-" === match[1] ? -1 : 1, duration = {
            y: 0,
            d: toInt(match[DATE]) * sign,
            h: toInt(match[HOUR]) * sign,
            m: toInt(match[MINUTE]) * sign,
            s: toInt(match[SECOND]) * sign,
            ms: toInt(match[MILLISECOND]) * sign
        }) : (match = isoDurationRegex.exec(input)) && (sign = "-" === match[1] ? -1 : 1, parseIso = function (inp) {
            var res = inp && parseFloat(inp.replace(",", "."));
            return (isNaN(res) ? 0 : res) * sign
        }, duration = {
            y: parseIso(match[2]),
            M: parseIso(match[3]),
            d: parseIso(match[4]),
            h: parseIso(match[5]),
            m: parseIso(match[6]),
            s: parseIso(match[7]),
            w: parseIso(match[8])
        }), ret = new Duration(duration), moment.isDuration(input) && input.hasOwnProperty("_lang") && (ret._lang = input._lang), ret
    }, moment.version = VERSION, moment.defaultFormat = isoFormat, moment.updateOffset = function () {
    }, moment.lang = function (key, values) {
        var r;
        return key ? (values ? loadLang(normalizeLanguage(key), values) : null === values ? (unloadLang(key), key = "en") : languages[key] || getLangDefinition(key), r = moment.duration.fn._lang = moment.fn._lang = getLangDefinition(key), r._abbr) : moment.fn._lang._abbr
    }, moment.langData = function (key) {
        return key && key._lang && key._lang._abbr && (key = key._lang._abbr), getLangDefinition(key)
    }, moment.isMoment = function (obj) {
        return obj instanceof Moment || null != obj && obj.hasOwnProperty("_isAMomentObject")
    }, moment.isDuration = function (obj) {
        return obj instanceof Duration
    }, i = lists.length - 1; i >= 0; --i) makeList(lists[i]);
    for (moment.normalizeUnits = function (units) {
        return normalizeUnits(units)
    }, moment.invalid = function (flags) {
        var m = moment.utc(0 / 0);
        return null != flags ? extend(m._pf, flags) : m._pf.userInvalidated = !0, m
    }, moment.parseZone = function (input) {
        return moment(input).parseZone()
    }, extend(moment.fn = Moment.prototype, {
        clone: function () {
            return moment(this)
        }, valueOf: function () {
            return +this._d + 6e4 * (this._offset || 0)
        }, unix: function () {
            return Math.floor(+this / 1e3)
        }, toString: function () {
            return this.clone().lang("en").format("ddd MMM DD YYYY HH:mm:ss [GMT]ZZ")
        }, toDate: function () {
            return this._offset ? new Date(+this) : this._d
        }, toISOString: function () {
            var m = moment(this).utc();
            return 0 < m.year() && m.year() <= 9999 ? formatMoment(m, "YYYY-MM-DD[T]HH:mm:ss.SSS[Z]") : formatMoment(m, "YYYYYY-MM-DD[T]HH:mm:ss.SSS[Z]")
        }, toArray: function () {
            var m = this;
            return [m.year(), m.month(), m.date(), m.hours(), m.minutes(), m.seconds(), m.milliseconds()]
        }, isValid: function () {
            return isValid(this)
        }, isDSTShifted: function () {
            return this._a ? this.isValid() && compareArrays(this._a, (this._isUTC ? moment.utc(this._a) : moment(this._a)).toArray()) > 0 : !1
        }, parsingFlags: function () {
            return extend({}, this._pf)
        }, invalidAt: function () {
            return this._pf.overflow
        }, utc: function () {
            return this.zone(0)
        }, local: function () {
            return this.zone(0), this._isUTC = !1, this
        }, format: function (inputString) {
            var output = formatMoment(this, inputString || moment.defaultFormat);
            return this.lang().postformat(output)
        }, add: function (input, val) {
            var dur;
            return dur = "string" == typeof input ? moment.duration(+val, input) : moment.duration(input, val), addOrSubtractDurationFromMoment(this, dur, 1), this
        }, subtract: function (input, val) {
            var dur;
            return dur = "string" == typeof input ? moment.duration(+val, input) : moment.duration(input, val), addOrSubtractDurationFromMoment(this, dur, -1), this
        }, diff: function (input, units, asFloat) {
            var diff, output, that = makeAs(input, this), zoneDiff = 6e4 * (this.zone() - that.zone());
            return units = normalizeUnits(units), "year" === units || "month" === units ? (diff = 432e5 * (this.daysInMonth() + that.daysInMonth()), output = 12 * (this.year() - that.year()) + (this.month() - that.month()), output += (this - moment(this).startOf("month") - (that - moment(that).startOf("month"))) / diff, output -= 6e4 * (this.zone() - moment(this).startOf("month").zone() - (that.zone() - moment(that).startOf("month").zone())) / diff, "year" === units && (output /= 12)) : (diff = this - that, output = "second" === units ? diff / 1e3 : "minute" === units ? diff / 6e4 : "hour" === units ? diff / 36e5 : "day" === units ? (diff - zoneDiff) / 864e5 : "week" === units ? (diff - zoneDiff) / 6048e5 : diff), asFloat ? output : absRound(output)
        }, from: function (time, withoutSuffix) {
            return moment.duration(this.diff(time)).lang(this.lang()._abbr).humanize(!withoutSuffix)
        }, fromNow: function (withoutSuffix) {
            return this.from(moment(), withoutSuffix)
        }, calendar: function () {
            var sod = makeAs(moment(), this).startOf("day"), diff = this.diff(sod, "days", !0),
                format = -6 > diff ? "sameElse" : -1 > diff ? "lastWeek" : 0 > diff ? "lastDay" : 1 > diff ? "sameDay" : 2 > diff ? "nextDay" : 7 > diff ? "nextWeek" : "sameElse";
            return this.format(this.lang().calendar(format, this))
        }, isLeapYear: function () {
            return isLeapYear(this.year())
        }, isDST: function () {
            return this.zone() < this.clone().month(0).zone() || this.zone() < this.clone().month(5).zone()
        }, day: function (input) {
            var day = this._isUTC ? this._d.getUTCDay() : this._d.getDay();
            return null != input ? (input = parseWeekday(input, this.lang()), this.add({d: input - day})) : day
        }, month: function (input) {
            var dayOfMonth, utc = this._isUTC ? "UTC" : "";
            return null != input ? "string" == typeof input && (input = this.lang().monthsParse(input), "number" != typeof input) ? this : (dayOfMonth = this.date(), this.date(1), this._d["set" + utc + "Month"](input), this.date(Math.min(dayOfMonth, this.daysInMonth())), moment.updateOffset(this), this) : this._d["get" + utc + "Month"]()
        }, startOf: function (units) {
            switch (units = normalizeUnits(units)) {
                case"year":
                    this.month(0);
                case"month":
                    this.date(1);
                case"week":
                case"isoWeek":
                case"day":
                    this.hours(0);
                case"hour":
                    this.minutes(0);
                case"minute":
                    this.seconds(0);
                case"second":
                    this.milliseconds(0)
            }
            return "week" === units ? this.weekday(0) : "isoWeek" === units && this.isoWeekday(1), this
        }, endOf: function (units) {
            return units = normalizeUnits(units), this.startOf(units).add("isoWeek" === units ? "week" : units, 1).subtract("ms", 1)
        }, isAfter: function (input, units) {
            return units = "undefined" != typeof units ? units : "millisecond", +this.clone().startOf(units) > +moment(input).startOf(units)
        }, isBefore: function (input, units) {
            return units = "undefined" != typeof units ? units : "millisecond", +this.clone().startOf(units) < +moment(input).startOf(units)
        }, isSame: function (input, units) {
            return units = units || "ms", +this.clone().startOf(units) === +makeAs(input, this).startOf(units)
        }, min: function (other) {
            return other = moment.apply(null, arguments), this > other ? this : other
        }, max: function (other) {
            return other = moment.apply(null, arguments), other > this ? this : other
        }, zone: function (input) {
            var offset = this._offset || 0;
            return null == input ? this._isUTC ? offset : this._d.getTimezoneOffset() : ("string" == typeof input && (input = timezoneMinutesFromString(input)), Math.abs(input) < 16 && (input = 60 * input), this._offset = input, this._isUTC = !0, offset !== input && addOrSubtractDurationFromMoment(this, moment.duration(offset - input, "m"), 1, !0), this)
        }, zoneAbbr: function () {
            return this._isUTC ? "UTC" : ""
        }, zoneName: function () {
            return this._isUTC ? "Coordinated Universal Time" : ""
        }, parseZone: function () {
            return this._tzm ? this.zone(this._tzm) : "string" == typeof this._i && this.zone(this._i), this
        }, hasAlignedHourOffset: function (input) {
            return input = input ? moment(input).zone() : 0, (this.zone() - input) % 60 === 0
        }, daysInMonth: function () {
            return daysInMonth(this.year(), this.month())
        }, dayOfYear: function (input) {
            var dayOfYear = round((moment(this).startOf("day") - moment(this).startOf("year")) / 864e5) + 1;
            return null == input ? dayOfYear : this.add("d", input - dayOfYear)
        }, quarter: function () {
            return Math.ceil((this.month() + 1) / 3)
        }, weekYear: function (input) {
            var year = weekOfYear(this, this.lang()._week.dow, this.lang()._week.doy).year;
            return null == input ? year : this.add("y", input - year)
        }, isoWeekYear: function (input) {
            var year = weekOfYear(this, 1, 4).year;
            return null == input ? year : this.add("y", input - year)
        }, week: function (input) {
            var week = this.lang().week(this);
            return null == input ? week : this.add("d", 7 * (input - week))
        }, isoWeek: function (input) {
            var week = weekOfYear(this, 1, 4).week;
            return null == input ? week : this.add("d", 7 * (input - week))
        }, weekday: function (input) {
            var weekday = (this.day() + 7 - this.lang()._week.dow) % 7;
            return null == input ? weekday : this.add("d", input - weekday)
        }, isoWeekday: function (input) {
            return null == input ? this.day() || 7 : this.day(this.day() % 7 ? input : input - 7)
        }, get: function (units) {
            return units = normalizeUnits(units), this[units]()
        }, set: function (units, value) {
            return units = normalizeUnits(units), "function" == typeof this[units] && this[units](value), this
        }, lang: function (key) {
            return key === undefined ? this._lang : (this._lang = getLangDefinition(key), this)
        }
    }), i = 0; i < proxyGettersAndSetters.length; i++) makeGetterAndSetter(proxyGettersAndSetters[i].toLowerCase().replace(/s$/, ""), proxyGettersAndSetters[i]);
    makeGetterAndSetter("year", "FullYear"), moment.fn.days = moment.fn.day, moment.fn.months = moment.fn.month, moment.fn.weeks = moment.fn.week, moment.fn.isoWeeks = moment.fn.isoWeek, moment.fn.toJSON = moment.fn.toISOString, extend(moment.duration.fn = Duration.prototype, {
        _bubble: function () {
            var seconds, minutes, hours, years, milliseconds = this._milliseconds, days = this._days,
                months = this._months, data = this._data;
            data.milliseconds = milliseconds % 1e3, seconds = absRound(milliseconds / 1e3), data.seconds = seconds % 60, minutes = absRound(seconds / 60), data.minutes = minutes % 60, hours = absRound(minutes / 60), data.hours = hours % 24, days += absRound(hours / 24), data.days = days % 30, months += absRound(days / 30), data.months = months % 12, years = absRound(months / 12), data.years = years
        }, weeks: function () {
            return absRound(this.days() / 7)
        }, valueOf: function () {
            return this._milliseconds + 864e5 * this._days + this._months % 12 * 2592e6 + 31536e6 * toInt(this._months / 12)
        }, humanize: function (withSuffix) {
            var difference = +this, output = relativeTime(difference, !withSuffix, this.lang());
            return withSuffix && (output = this.lang().pastFuture(difference, output)), this.lang().postformat(output)
        }, add: function (input, val) {
            var dur = moment.duration(input, val);
            return this._milliseconds += dur._milliseconds, this._days += dur._days, this._months += dur._months, this._bubble(), this
        }, subtract: function (input, val) {
            var dur = moment.duration(input, val);
            return this._milliseconds -= dur._milliseconds, this._days -= dur._days, this._months -= dur._months, this._bubble(), this
        }, get: function (units) {
            return units = normalizeUnits(units), this[units.toLowerCase() + "s"]()
        }, as: function (units) {
            return units = normalizeUnits(units), this["as" + units.charAt(0).toUpperCase() + units.slice(1) + "s"]()
        }, lang: moment.fn.lang, toIsoString: function () {
            var years = Math.abs(this.years()), months = Math.abs(this.months()), days = Math.abs(this.days()),
                hours = Math.abs(this.hours()), minutes = Math.abs(this.minutes()),
                seconds = Math.abs(this.seconds() + this.milliseconds() / 1e3);
            return this.asSeconds() ? (this.asSeconds() < 0 ? "-" : "") + "P" + (years ? years + "Y" : "") + (months ? months + "M" : "") + (days ? days + "D" : "") + (hours || minutes || seconds ? "T" : "") + (hours ? hours + "H" : "") + (minutes ? minutes + "M" : "") + (seconds ? seconds + "S" : "") : "P0D"
        }
    });
    for (i in unitMillisecondFactors) unitMillisecondFactors.hasOwnProperty(i) && (makeDurationAsGetter(i, unitMillisecondFactors[i]), makeDurationGetter(i.toLowerCase()));
    makeDurationAsGetter("Weeks", 6048e5), moment.duration.fn.asMonths = function () {
        return (+this - 31536e6 * this.years()) / 2592e6 + 12 * this.years()
    }, moment.lang("en", {
        ordinal: function (number) {
            var b = number % 10,
                output = 1 === toInt(number % 100 / 10) ? "th" : 1 === b ? "st" : 2 === b ? "nd" : 3 === b ? "rd" : "th";
            return number + output
        }
    }), hasModule ? (module.exports = moment, makeGlobal(!0)) : "function" == typeof define && define.amd ? define("moment", function (require, exports, module) {
        return module.config && module.config() && module.config().noGlobal !== !0 && makeGlobal(module.config().noGlobal === undefined), moment
    }) : makeGlobal()
}).call(this);