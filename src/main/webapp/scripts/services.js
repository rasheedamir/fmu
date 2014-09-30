'use strict';

/* Services */
angular.module('fmuClientApp')
    .factory('OrderService', ['$q', '$http', 'RESTURL',
        function($q, $http) { //, RESTURL) {
            return {
                getEavrops: function() {
                    return $http.get('/' /*RESTURL.eavrop*/ ).then(function() { //data) {
                        // Success
                        //return data.data;

                        // utredare ska bort
                        return [{
                            'arendeId': '123421',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1445451264483,
                            'patientCity': 'Farsta',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan om utredning har inkommit',
                            'antalDagarEfterForfragan': 3,
                            'color': null
                        }, {
                            'arendeId': '753423',
                            'utredningType': 'SLU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'creationTime': 1490811264484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan tilldelas, inväntar acceptans',
                            'antalDagarEfterForfragan': 5,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '44240',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1481310864484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan ej accepterad av utredare',
                            'antalDagarEfterForfragan': 10,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': '78743',
                            'utredningType': 'TMU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Malmö',
                            'creationTime': 1492884864484,
                            'patientCity': 'malmö',
                            'mottagarenOrganisation': 'Region Skåne',
                            'status': 'In progress',
                            'antalDagarEfterForfragan': 15,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '123421',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1445451264483,
                            'patientCity': 'Farsta',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan om utredning har inkommit',
                            'antalDagarEfterForfragan': 3,
                            'color': null
                        }, {
                            'arendeId': '753423',
                            'utredningType': 'SLU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'creationTime': 1490811264484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan tilldelas, inväntar acceptans',
                            'antalDagarEfterForfragan': 5,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '44240',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1481310864484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan ej accepterad av utredare',
                            'antalDagarEfterForfragan': 10,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': '78743',
                            'utredningType': 'TMU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Malmö',
                            'creationTime': 1492884864484,
                            'patientCity': 'malmö',
                            'mottagarenOrganisation': 'Region Skåne',
                            'status': 'In progress',
                            'antalDagarEfterForfragan': 15,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '123421',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1445451264483,
                            'patientCity': 'Farsta',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan om utredning har inkommit',
                            'antalDagarEfterForfragan': 3,
                            'color': null
                        }, {
                            'arendeId': '753423',
                            'utredningType': 'SLU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'creationTime': 1490811264484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan tilldelas, inväntar acceptans',
                            'antalDagarEfterForfragan': 5,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '44240',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1481310864484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan ej accepterad av utredare',
                            'antalDagarEfterForfragan': 10,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': '78743',
                            'utredningType': 'TMU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Malmö',
                            'creationTime': 1492884864484,
                            'patientCity': 'malmö',
                            'mottagarenOrganisation': 'Region Skåne',
                            'status': 'In progress',
                            'antalDagarEfterForfragan': 15,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '123421',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1445451264483,
                            'patientCity': 'Farsta',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan om utredning har inkommit',
                            'antalDagarEfterForfragan': 3,
                            'color': null
                        }, {
                            'arendeId': '753423',
                            'utredningType': 'SLU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'creationTime': 1490811264484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan tilldelas, inväntar acceptans',
                            'antalDagarEfterForfragan': 5,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '44240',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1481310864484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan ej accepterad av utredare',
                            'antalDagarEfterForfragan': 10,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': '78743',
                            'utredningType': 'TMU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Malmö',
                            'creationTime': 1492884864484,
                            'patientCity': 'malmö',
                            'mottagarenOrganisation': 'Region Skåne',
                            'status': 'In progress',
                            'antalDagarEfterForfragan': 15,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '123421',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1445451264483,
                            'patientCity': 'Farsta',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan om utredning har inkommit',
                            'antalDagarEfterForfragan': 3,
                            'color': null
                        }, {
                            'arendeId': '753423',
                            'utredningType': 'SLU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'creationTime': 1490811264484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan tilldelas, inväntar acceptans',
                            'antalDagarEfterForfragan': 5,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '44240',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1481310864484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan ej accepterad av utredare',
                            'antalDagarEfterForfragan': 10,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': '78743',
                            'utredningType': 'TMU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Malmö',
                            'creationTime': 1492884864484,
                            'patientCity': 'malmö',
                            'mottagarenOrganisation': 'Region Skåne',
                            'status': 'In progress',
                            'antalDagarEfterForfragan': 15,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '123421',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1445451264483,
                            'patientCity': 'Farsta',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan om utredning har inkommit',
                            'antalDagarEfterForfragan': 3,
                            'color': null
                        }, {
                            'arendeId': '753423',
                            'utredningType': 'SLU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'creationTime': 1490811264484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan tilldelas, inväntar acceptans',
                            'antalDagarEfterForfragan': 5,
                            'color': 'bg-warning'
                        }, {
                            'arendeId': '44240',
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm Söderort',
                            'creationTime': 1481310864484,
                            'patientCity': 'Stockholm',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Förfrågan ej accepterad av utredare',
                            'antalDagarEfterForfragan': 10,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': '78743',
                            'utredningType': 'TMU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Malmö',
                            'creationTime': 1492884864484,
                            'patientCity': 'malmö',
                            'mottagarenOrganisation': 'Region Skåne',
                            'status': 'In progress',
                            'antalDagarEfterForfragan': 15,
                            'color': 'bg-warning'
                        }];
                    }, function(err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
                }
            };
        }
    ])
    .factory('OnGoingService', ['$q', '$http', 'RESTURL',
        function($q, $http) { //, RESTURL) {
            return {
                getEavrops: function() {
                    return $http.get('/' /*RESTURL.eavrop*/ ).then(function() { //data) {
                        // Success
                        //return data.data;

                        // utredare ska bort
                        return [{
                            'arendeId': 34453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1445451264483,
                            'daysPassed': 17,
                            'avikelser': 0,
                            'color': null
                        }, {
                            'arendeId': 53453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utförare utsedd, inväntar handlingar i ärendet',
                            'startDate': 1490811264484,
                            'daysPassed': 17,
                            'avikelser': 39,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': 563453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utförare utsedd, inväntar handlingar i ärendet',
                            'startDate': 1481310864484,
                            'daysPassed': 17,
                            'avikelser': 37,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': 093453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1465451264483,
                            'daysPassed': 17,
                            'avikelser': 0,
                            'color': null
                        }, {
                            'arendeId': 6873453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1785451264483,
                            'daysPassed': 17,
                            'avikelser': 0,
                            'color': null
                        }, {
                            'arendeId':3453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1455451264483,
                            'daysPassed': 17,
                            'avikelser': 0,
                            'color': null
                        }, {
                            'arendeId': 8554453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1675451264483,
                            'daysPassed': 17,
                            'avikelser': 0,
                            'color': null
                        }, {
                            'arendeId': 9983453621,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1575451264483,
                            'daysPassed': 17,
                            'avikelser': 27,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': 67983475084375,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utförare utsedd, inväntar handlingar i ärendet',
                            'startDate': 1125451264483,
                            'daysPassed': 17,
                            'avikelser': 51,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': 89346984369,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1455451264483,
                            'daysPassed': 17,
                            'avikelser': 0,
                            'color': null
                        }, {
                            'arendeId': 294850947,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Arbetsförmedlingen',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1225451264483,
                            'daysPassed': 17,
                            'avikelser': 36,
                            'color': 'bg-danger'
                        }, {
                            'arendeId': 764576,
                            'utredningType': 'AFU',
                            'bestallareOrganisation': 'Försäkringskassan',
                            'enhet': 'Stockholm City',
                            'mottagarenOrganisation': 'Stockholms Läns Landsting',
                            'status': 'Utredning påbörjat',
                            'startDate': 1335451264483,
                            'daysPassed': 17,
                            'avikelser': 0,
                            'color': null
                        }];
                    }, function(err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
                }
            };
        }
    ])

.factory('OverviewCompletedService', ['$q', '$http', 'RESTURL',
    function($q, $http) { //, RESTURL) {
        return {
            getEavrops: function() {
                return $http.get('/' /*RESTURL.eavrop*/ ).then(function() { //data) {
                    // Success
                    //return data.data;

                    // utredare ska bort
                    return [{
                        'arendeId': 87512462745,
                        'utredningType': 'AFU',
                        'totalDaysPassed': 20,
                        'totalCompletionDays': 0,
                        'avikelser': 0,
                        'UtredareOrganisation': 'Vårdenheten K',
                        'utredareAnsvarig': 'Anna Ang',
                        'isCompleted': 'ja',
                        'dateDelivered': 1335451212483,
                        'approvedDate': 1335451264483,
                        'color': 'bg-success'
                    }, {
                        'arendeId': 12104462745,
                        'utredningType': 'AFU',
                        'totalDaysPassed': 20,
                        'totalCompletionDays': 0,
                        'avikelser': 0,
                        'UtredareOrganisation': 'Vårdenheten C',
                        'utredareAnsvarig': 'Assar Sverin',
                        'isCompleted': 'ja',
                        'dateDelivered': 1335451164483,
                        'approvedDate': 1335451264483,
                        'color': 'bg-success'
                    }, {
                        'arendeId': 246352745,
                        'utredningType': 'AFU',
                        'totalDaysPassed': 20,
                        'totalCompletionDays': 0,
                        'avikelser': 0,
                        'UtredareOrganisation': 'Danderyds sjukhus',
                        'utredareAnsvarig': 'Ola Svensson',
                        'isCompleted': 'ja',
                        'dateDelivered': 1335450964483,
                        'approvedDate': 1335451264483,
                        'color': 'bg-success'
                    }, {
                        'arendeId': 34352745,
                        'utredningType': 'AFU',
                        'totalDaysPassed': 20,
                        'totalCompletionDays': 0,
                        'avikelser': 0,
                        'UtredareOrganisation': 'Vårdenheten X',
                        'utredareAnsvarig': 'Åsa Lin',
                        'isCompleted': 'ja',
                        'dateDelivered': 1335451244483,
                        'approvedDate': 1335451264483,
                        'color': 'bg-success'
                    }, {
                        'arendeId': 124242745,
                        'utredningType': 'AFU',
                        'totalDaysPassed': 20,
                        'totalCompletionDays': 5,
                        'avikelser': 1,
                        'UtredareOrganisation': 'Vårdenheten Y',
                        'utredareAnsvarig': 'Karl Linder',
                        'isCompleted': 'nej',
                        'dateDelivered': 1335451214483,
                        'approvedDate': null,
                        'color': 'bg-warning'
                    }, {
                        'arendeId': 12462745,
                        'utredningType': 'AFU',
                        'totalDaysPassed': 30,
                        'totalCompletionDays': 10,
                        'avikelser': 10,
                        'UtredareOrganisation': 'Danderyds sjukhus',
                        'utredareAnsvarig': 'Karl Linder',
                        'isCompleted': 'nej',
                        'dateDelivered': 1335451124483,
                        'approvedDate': null,
                        'color': 'bg-danger'
                    }];
                }, function(err) {
                    // Failed to retrieve data
                    return $q.reject(err.data);
                });
            }
        };
    }
])


.factory('DatePickerService', ['$filter',
    function($filter) {
        var service = {};
        service.dateFormat = 'dd-MM-yyyy';

        service.setScope = function(newScope) {
            this.scope = newScope;
        };

        service.getTableData = function() {
            return this.scope.tableData;
        };

        service.getDateKey = function() {
            return this.scope.dateKey;
        };

        service.calculateInitialDateRange = function() {
            var data = this.getTableData();
            var dateKey = this.getDateKey();

            if (!data || data.length === 0) {
                return;
            }

            if (data.length === 1) {
                this.update(_.first(data[dateKey], _.first(data)[dateKey]));
            } else {
                var ordered = $filter('orderBy')(_.filter(data, function(current){return current[dateKey] !== null;}), dateKey, false);
                this.update(_.first(ordered)[dateKey], _.last(ordered)[dateKey]);
            }
        };

        // Clear date selection
        service.clearStartDate = function() {
            this.scope.startDate = null;
        };

        service.clearEndDate = function() {
            this.scope.endDate = null;
        };

        // Disable weekend selection
        service.disabled = function(date, mode) {
            return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
        };

        service.openStart = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            this.startDateOpened = {
                value: true
            };
            this.endDateOpened = {
                value: false
            };

        };

        service.openEnd = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            this.endDateOpened = {
                value: true
            };
            this.startDateOpened = {
                value: false
            };
        };

        // Date picker specific function
        service.update = function(date1, date2) {
            this.scope.startDate = date1;
            this.scope.endDate = date2;
        };

        service.getFormattedDate = function(date) {
            if(!date){
                return '';
            }
            return $filter('date')(date, 'dd-MM-yyyy');
        };

        return service;
    }
])

.factory('TableService', ['ngTableParams', '$filter',
    function(ngTableParams, $filter) {
        var service = {};

        service.setScope = function(newScope) {
            this.scope = newScope;
        };

        service.getDateKey = function() {
            if (!this.scope) {
                return null;
            }
            return this.scope.dateKey;
        };

        service.getStartDate = function() {
            if (!this.scope) {
                return null;
            }
            return this.scope.startDate;
        };

        service.getEndDate = function() {
            if (!this.scope) {
                return null;
            }
            return this.scope.endDate;
        };

        service.getTableData = function() {
            if (!this.scope || !this.scope.tableData) {
                return [];
            }

            return this.scope.tableData;
        };

        service.getDateFilteredData = function() {
            var data = this.getTableData();

            if (data.length > 1) {
                return $filter('dateFilter')(data, this.getDateKey(), this.getStartDate(), this.getEndDate());
            } else {
                return data;
            }
        };

        service.getTableParameters = function() {
            if (!this.scope.tableParams) {
                return null;
            }

            return this.scope.tableParams;
        };

        service.sort = function(key) {
            var params = {};
            params[key] = this.getTableParameters().isSortBy(key, 'asc') ? 'desc' : 'asc';
            this.getTableParameters().sorting(params);
        };

        service.doDateFilter = function() {
            this.getTableParameters().reload();
        };

        service.getFields = function() {
            var self = this;
            var result = [];
            _.each(this.scope.headerGroups, function(group) {
                _.each(group.children, function(child) {
                    if (_.intersection(child.restricted, self.scope.authService.userInfo.roles).length > 0){
                        result.push(child);
                    }
                });
            });

            return result;
        };

        service.restrictedFields = function(fields) {
            var self = this;
            var total = 0;
            _.each(fields, function(field) {
                total = _.intersection(field.restricted, self.scope.authService.userInfo.roles).length > 0 ? total : total + 1;
            });

            return total;
        };

        service.initTableParameters = function() {
            var self = this;
            if (!self.getTableParameters()) {

                /* jshint -W055 */ // XXX: ngTableParams.
                self.scope.tableParams = new ngTableParams({
                    page: 1, // show first page
                    count: 10 // count per page
                }, {
                    total: self.getDateFilteredData().length,
                    getData: function($defer, params) {
                        var filteredData = params.sorting() ?
                            $filter('orderBy')(self.getDateFilteredData(), params.orderBy()) :
                            self.getDateFilteredData();

                        params.total(filteredData.length);
                        $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                    },
                    $scope: self.scope
                });
                /* jshint +W055 */
            }
        };

        return service;
    }
]);
