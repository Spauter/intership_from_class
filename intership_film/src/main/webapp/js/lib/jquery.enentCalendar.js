$.fn.eventCalendar = function (options) {
    function sortJson(a, b) {
        return a.date.toLowerCase() > b.date.toLowerCase() ? 1 : -1
    }

    function dateSlider(show, year, month) {
        var $eventsCalendarSlider = $("<div class='eventsCalendar-slider'></div>"),
            $eventsCalendarMonthWrap = $("<div class='eventsCalendar-monthWrap'></div>"),
            $eventsCalendarTitle = $("<div class='eventsCalendar-currentTitle'><a href='#' class='monthTitle'></a></div>"),
            $eventsCalendarArrows = $("<a href='#' class='arrow prev'><span>" + eventsOpts.txt_prev + "</span></a><a href='#' class='arrow next'><span>" + eventsOpts.txt_next + "</span></a>");
        if ($eventsCalendarDaysList = $("<ul class='eventsCalendar-daysList'></ul>"), date = new Date, flags.wrap.find(".eventsCalendar-slider").size() ? flags.wrap.find(".eventsCalendar-slider").append($eventsCalendarMonthWrap) : (flags.wrap.prepend($eventsCalendarSlider), $eventsCalendarSlider.append($eventsCalendarMonthWrap)), flags.wrap.find(".eventsCalendar-monthWrap.currentMonth").removeClass("currentMonth").addClass("oldMonth"), $eventsCalendarMonthWrap.addClass("currentMonth").append($eventsCalendarTitle, $eventsCalendarDaysList), "current" === show) day = date.getDate(), $eventsCalendarSlider.append($eventsCalendarArrows); else {
            date = new Date(flags.wrap.attr("data-current-year"), flags.wrap.attr("data-current-month"), 1, 0, 0, 0), day = 0, moveOfMonth = 1, "prev" === show && (moveOfMonth = -1), date.setMonth(date.getMonth() + moveOfMonth);
            var tmpDate = new Date;
            date.getMonth() === tmpDate.getMonth() && (day = tmpDate.getDate())
        }
        var year = date.getFullYear(), currentYear = (new Date).getFullYear(), month = date.getMonth();
        "current" != show && getEvents(eventsOpts.eventsLimit, year, month, !1, show), flags.wrap.attr("data-current-month", month).attr("data-current-year", year), $eventsCalendarTitle.find(".monthTitle").html(eventsOpts.monthNames[month] + " " + year);
        var daysOnTheMonth = 32 - new Date(year, month, 32).getDate(), daysList = [];
        if (eventsOpts.showDayAsWeeks) {
            if ($eventsCalendarDaysList.addClass("showAsWeek"), eventsOpts.showDayNameInCalendar) {
                $eventsCalendarDaysList.addClass("showDayNames");
                var i = 0;
                for (eventsOpts.startWeekOnMonday && (i = 1); 7 > i; i++) daysList.push('<li class="eventsCalendar-day-header">' + eventsOpts.dayNamesShort[i] + "</li>"), 6 === i && eventsOpts.startWeekOnMonday && daysList.push('<li class="eventsCalendar-day-header">' + eventsOpts.dayNamesShort[0] + "</li>")
            }
            dt = new Date(year, month, 1);
            var weekDay = dt.getDay();
            for (eventsOpts.startWeekOnMonday && (weekDay = dt.getDay() - 1), 0 > weekDay && (weekDay = 6), i = weekDay; i > 0; i--) daysList.push('<li class="eventsCalendar-day empty"></li>')
        }
        for (dayCount = 1; daysOnTheMonth >= dayCount; dayCount++) {
            var dayClass = "";
            day > 0 && dayCount === day && year === currentYear && (dayClass = "today"), daysList.push('<li id="dayList_' + dayCount + '" rel="' + dayCount + '" class="eventsCalendar-day ' + dayClass + '"><a href="#">' + dayCount + "</a></li>")
        }
        $eventsCalendarDaysList.append(daysList.join("")), $eventsCalendarSlider.css("height", $eventsCalendarMonthWrap.height() + "px")
    }

    function num_abbrev_str(num) {
        var abbrev, len = num.length, last_char = num.charAt(len - 1);
        return abbrev = 2 === len && "1" === num.charAt(0) ? "th" : "1" === last_char ? "st" : "2" === last_char ? "nd" : "3" === last_char ? "rd" : "th", num + abbrev
    }

    function getEvents(limit, year, month, day, direction) {
        var limit = limit || 0, year = year || "", day = day || "";
        if ("undefined" != typeof month) var month = month; else var month = "";
        flags.wrap.find(".eventsCalendar-loading").fadeIn(), eventsOpts.jsonData ? (eventsOpts.cacheJson = !0, flags.eventsJson = eventsOpts.jsonData, getEventsData(flags.eventsJson, limit, year, month, day, direction)) : eventsOpts.cacheJson && direction ? getEventsData(flags.eventsJson, limit, year, month, day, direction) : $.getJSON(eventsOpts.eventsjson + "?limit=" + limit + "&year=" + year + "&month=" + month + "&day=" + day, function (data) {
            flags.eventsJson = data, getEventsData(flags.eventsJson, limit, year, month, day, direction)
        }).error(function () {
            showError("error getting json: ")
        }), day > "" && (flags.wrap.find(".current").removeClass("current"), flags.wrap.find("#dayList_" + day).addClass("current"))
    }

    function getEventsData(data, limit, year, month, day, direction) {
        directionLeftMove = "-=" + flags.directionLeftMove, eventContentHeight = "auto", subtitle = flags.wrap.find(".eventsCalendar-list-wrap .eventsCalendar-subtitle"), direction ? ("" != day ? subtitle.html(eventsOpts.txt_SpecificEvents_prev + eventsOpts.monthNames[month] + " " + num_abbrev_str(day) + " " + eventsOpts.txt_SpecificEvents_after) : subtitle.html(eventsOpts.txt_SpecificEvents_prev + eventsOpts.monthNames[month] + " " + eventsOpts.txt_SpecificEvents_after), "prev" === direction ? directionLeftMove = "+=" + flags.directionLeftMove : ("day" === direction || "month" === direction) && (directionLeftMove = "+=0", eventContentHeight = 0)) : (subtitle.html(eventsOpts.txt_NextEvents), eventContentHeight = "auto", directionLeftMove = "-=0"), flags.wrap.find(".eventsCalendar-list").animate({
            opacity: eventsOpts.moveOpacity,
            left: directionLeftMove,
            height: eventContentHeight
        }, eventsOpts.moveSpeed, function () {
            flags.wrap.find(".eventsCalendar-list").css({left: 0, height: "auto"}).hide();
            var events = [];
            if (data = $(data).sort(sortJson), data.length) {
                var eventDescClass = "";
                eventsOpts.showDescription || (eventDescClass = "hidden");
                var eventLinkTarget = "_self";
                eventsOpts.openEventInNewWindow && (eventLinkTarget = "_target");
                var i = 0;
                $.each(data, function (key, event) {
                    if ("human" == eventsOpts.jsonDateFormat) var eventDateTime = event.date.split(" "),
                        eventDate = eventDateTime[0].split("-"), eventTime = eventDateTime[1].split(":"),
                        eventYear = eventDate[0], eventMonth = parseInt(eventDate[1]) - 1,
                        eventDay = parseInt(eventDate[2]), eventMonthToShow = parseInt(eventMonth) + 1,
                        eventHour = eventTime[0], eventMinute = eventTime[1], eventSeconds = eventTime[2],
                        eventDate = new Date(eventYear, eventMonth, eventDay, eventHour, eventMinute, eventSeconds); else var eventDate = new Date(parseInt(event.date)),
                        eventYear = eventDate.getFullYear(), eventMonth = eventDate.getMonth(),
                        eventDay = eventDate.getDate(), eventMonthToShow = eventMonth + 1,
                        eventHour = eventDate.getHours(), eventMinute = eventDate.getMinutes();
                    if (parseInt(eventMinute) <= 9 && (eventMinute = "0" + parseInt(eventMinute)), (0 === limit || limit > i) && !(month !== !1 && month != eventMonth || "" != day && day != eventDay || "" != year && year != eventYear)) if (month === !1 && eventDate < new Date) ; else {
                        if (eventStringDate = eventDay + "/" + eventMonthToShow + "/" + eventYear, event.url) var eventTitle = '<a href="' + event.url + '" target="' + eventLinkTarget + '" class="eventTitle">' + event.title + "</a>"; else var eventTitle = '<span class="eventTitle">' + event.title + "</span>";
                        events.push('<li id="' + key + '" class="' + event.type + '"><time datetime="' + eventDate + '"><em>' + eventStringDate + "</em><small>" + eventHour + ":" + eventMinute + "</small></time>" + eventTitle + '<p class="eventDesc ' + eventDescClass + '">' + event.description + "</p></li>"), i++
                    }
                    eventYear == flags.wrap.attr("data-current-year") && eventMonth == flags.wrap.attr("data-current-month") && flags.wrap.find(".currentMonth .eventsCalendar-daysList #dayList_" + parseInt(eventDay)).addClass("dayWithEvents")
                })
            }
            events.length || events.push('<li class="eventsCalendar-noEvents"><p>' + eventsOpts.txt_noEvents + "</p></li>"), flags.wrap.find(".eventsCalendar-loading").hide(), flags.wrap.find(".eventsCalendar-list").html(events.join("")), flags.wrap.find(".eventsCalendar-list").animate({
                opacity: 1,
                height: "toggle"
            }, eventsOpts.moveSpeed)
        }), setCalendarWidth()
    }

    function changeMonth() {
        flags.wrap.find(".arrow").click(function (e) {
            if (e.preventDefault(), $(this).hasClass("next")) {
                dateSlider("next");
                var lastMonthMove = "-=" + flags.directionLeftMove
            } else {
                dateSlider("prev");
                var lastMonthMove = "+=" + flags.directionLeftMove
            }
            flags.wrap.find(".eventsCalendar-monthWrap.oldMonth").animate({
                opacity: eventsOpts.moveOpacity,
                left: lastMonthMove
            }, eventsOpts.moveSpeed, function () {
                flags.wrap.find(".eventsCalendar-monthWrap.oldMonth").remove()
            })
        })
    }

    function showError(msg) {
        flags.wrap.find(".eventsCalendar-list-wrap").html("<span class='eventsCalendar-loading error'>" + msg + " " + eventsOpts.eventsjson + "</span>")
    }

    function setCalendarWidth() {
        flags.directionLeftMove = flags.wrap.width(), flags.wrap.find(".eventsCalendar-monthWrap").width(flags.wrap.width() + "px"), flags.wrap.find(".eventsCalendar-list-wrap").width(flags.wrap.width() + "px")
    }

    var eventsOpts = $.extend({}, $.fn.eventCalendar.defaults, options),
        flags = {wrap: "", directionLeftMove: "300", eventsJson: {}};
    this.each(function () {
        flags.wrap = $(this), flags.wrap.addClass("eventCalendar-wrap").append("<div class='eventsCalendar-list-wrap'><p class='eventsCalendar-subtitle'></p><span class='eventsCalendar-loading'>loading...</span><div class='eventsCalendar-list-content'><ul class='eventsCalendar-list'></ul></div></div>"), eventsOpts.eventsScrollable && flags.wrap.find(".eventsCalendar-list-content").addClass("scrollable"), setCalendarWidth(), $(window).resize(function () {
            setCalendarWidth()
        }), dateSlider("current"), getEvents(eventsOpts.eventsLimit, !1, !1, !1, !1), changeMonth(), flags.wrap.on("click", ".eventsCalendar-day a", function (e) {
            e.preventDefault();
            var year = flags.wrap.attr("data-current-year"), month = flags.wrap.attr("data-current-month"),
                day = $(this).parent().attr("rel");
            getEvents(!1, year, month, day, "day")
        }), flags.wrap.on("click", ".monthTitle", function (e) {
            e.preventDefault();
            var year = flags.wrap.attr("data-current-year"), month = flags.wrap.attr("data-current-month");
            getEvents(eventsOpts.eventsLimit, year, month, !1, "month")
        })
    }), flags.wrap.find(".eventsCalendar-list").on("click", ".eventTitle", function (e) {
        if (!eventsOpts.showDescription) {
            e.preventDefault();
            var desc = $(this).parent().find(".eventDesc");
            if (!desc.find("a").size()) {
                var eventUrl = $(this).attr("href"), eventTarget = $(this).attr("target");
                desc.append('<a href="' + eventUrl + '" target="' + eventTarget + '" class="bt">' + eventsOpts.txt_GoToEventUrl + "</a>")
            }
            desc.is(":visible") ? desc.slideUp() : (eventsOpts.onlyOneDescription && flags.wrap.find(".eventDesc").slideUp(), desc.slideDown())
        }
    })
}, $.fn.eventCalendar.defaults = {
    eventsjson: "js/events.json",
    eventsLimit: 4,
    monthNames: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
    dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
    dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    txt_noEvents: "There are no events in this period",
    txt_SpecificEvents_prev: "",
    txt_SpecificEvents_after: "events:",
    txt_next: "next",
    txt_prev: "prev",
    txt_NextEvents: "Next events:",
    txt_GoToEventUrl: "See the event",
    showDayAsWeeks: !0,
    startWeekOnMonday: !0,
    showDayNameInCalendar: !0,
    showDescription: !1,
    onlyOneDescription: !0,
    openEventInNewWindow: !1,
    eventsScrollable: !1,
    jsonDateFormat: "timestamp",
    moveSpeed: 500,
    moveOpacity: .15,
    jsonData: "",
    cacheJson: !0
};