/* Magic Mirror Config Sample
 *
 * By Michael Teeuw http://michaelteeuw.nl
 * MIT Licensed.
 */

var config = {
        port: 8080,
        ipWhitelist: ["127.0.0.1", "::ffff:127.0.0.1", "::1"],

        language: 'en',
        timeFormat: 12,
        units: 'imperial',

        modules: [
                {
                        module: 'alert',
                },
                {
                        module: "updatenotification",
                        position: "top_bar"
                },
                {
                        module: 'clock',
                        position: 'top_left',
                        config: {
                                showPeriodUpper: 'true',
                                timezone: 'America/New_York'
                        }
                },
                {
                        module: 'calendar',
                        header: 'Google Calendar',
                        position: 'top_left',
                        config: {
                                fadePoint: '1',
                                timeFormat: 'absolute',
                                calendars: [
                                        {
                                                symbol: 'calendar-check-o ',
                                                url: 'https://calendar.google.com/calendar/ical/nicolekatz0615%40gmail.com/private-97aef6f30937588616f025080838a9d6/basic.ics',
                                                fade: 'false'
                                        }
                                ]
                        }
                },
                {
                        module: 'compliments',
                        position: 'upper_third',
                        config: {
                                compliments: {
                                        anytime: [
                                                "I Love You Nicole."
                                        ],
                                        morning: [
                                                "*Fist bump* balalalala",
                                                "Goodmorning Baby”
                                        ],
                                        afternoon: [
                                                "HI MOM THE FUTURE IS NOW - Kristi”
                                        ],
                                        evening: [
                                                "Goodnight LOVE <3",
                                                "Watch Star Wars!!! - Daniela"
                                        ],
                                        rain: [
                                                "It's fine, everything is fine"
                                        ],
                                        Sunday: [
                                        ],
                                        Monday: [
                                                "We are just here so we don't get fined - Briana!"
                                        ],
                                        Tuesday: [
                                        ],
                                        Wednesday: [
                                        ],
                                        Thursday: [
                                        ],
                                        Friday: [
                                        ],
                                        Saturday: [
                                        ],
                                        Sunday_morning: [
                                                "Wanna go to pinecrest? - Marissa"
                                        ],
                                        Sunday_afternoon: [],
                                        Sunday_evening: [],
                                        Monday_morning: [],
                                        Monday_afternoon: [],
                                        Monday_evening: [],
                                        Tuesday_morning: [],
                                        Tuesday_afternoon: [],
                                        Tuesday_evening: [],
                                        Wednesday_morning: [],
                                        Wednesday_afternoon: [],
                                        Wednesday_evening: [],
                                        Thursday_morning: [],
                                        Thursday_afternoon: [],
                                        Thursday_evening: [],
                                        Friday_morning: [],
                                        Friday_afternoon: [],
                                        Friday_evening: [],
                                        Saturday_morning: [],
                                        Saturday_afternoon: [],
                                        Saturday_evening: []
                                }
                        }
                },
                {
                        module: 'currentweather',
                        position: 'top_right',
                        config: {
                                location: 'Coral Gables',
                                locationID: '4151871',  //ID from http://www.openweathermap.org
                                appid: '0f3d390afc03b270c73df921133d2742',
                                showHumidity: 'true',
                                showPeriodUpper: 'true'
                        }
                },
                {
                        module: 'weatherforecast',
                        position: 'top_right',
                        header: 'Weather Forecast',
                        config: {
                                location: 'Coral Gables',
                                locationID: '4151871',  //ID from http://www.openweathermap.org
                                appid: '0f3d390afc03b270c73df921133d2742'
                        }
                },
                {
                        module: 'newsfeed',
                        position: 'bottom_bar',
                        config: {
                                feeds: [
                                        {
                                                title: "New York Times",
                                                url: "http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml"
                                        }
                                ],
                                showSourceTitle: true,
                                showPublishDate: true
                        }
                },
                {
                        module: 'helloworld',
                        position: 'bottom_bar',
                        config: {
                                text: "Go get that job at Disney! <3<3<3"

                        }
                },
//              {
//                      module: "MMM-Advent",
//                      position: "bottom_left",
//                      config: {
//                              marks: "4",
//                              start: "2017-02-09 21:00:00",
//                              end: "2017-02-13 10:00:00",
//                              enableAnimation: 'false',
//                              showFlameBeforeStart: 'false'
//                      }
//              },
        ]

};

/*************** DO NOT EDIT THE LINE BELOW ***************/
if (typeof module !== 'undefined') {module.exports = config;}

