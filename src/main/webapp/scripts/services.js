'use strict';

/* Services */
angular.module('fmuClientApp')
    .factory('OrderService', ['$q', '$http', 'RESTURL',
        function ($q, $http) {//, RESTURL) {
            return {
                getEavrops: function () {
                    return $http.get('/'/*RESTURL.eavrop*/).then(function () {//data) {
                        // Success
                        //return data.data;

                        // utredare ska bort
                        return [
                            {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                            {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                            {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                            {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                            {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                            {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                            {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                            {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                            {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                            {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                            {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                            {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                            {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                            {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                            {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                            {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                            {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                            {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                            {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                            {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                            {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                            {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                            {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                            {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'}
                        ];
                    }, function (err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
                }
            };
        }])
    .factory('OnGoingService', ['$q', '$http', 'RESTURL',
        function ($q, $http) {//, RESTURL) {
            return {
                getEavrops: function () {
                    return $http.get('/'/*RESTURL.eavrop*/).then(function () {//data) {
                        // Success
                        //return data.data;

                        // utredare ska bort
                        return [
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1445451264483, 'daysPassed': 17, 'avikelser': 0, 'color': null},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utförare utsedd, inväntar handlingar i ärendet', 'startDate': 1490811264484, 'daysPassed': 17, 'avikelser': 39, 'color': 'bg-danger'},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utförare utsedd, inväntar handlingar i ärendet', 'startDate': 1481310864484, 'daysPassed': 17, 'avikelser': 37, 'color': 'bg-danger'},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1465451264483, 'daysPassed': 17, 'avikelser': 0, 'color': null},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1785451264483, 'daysPassed': 17, 'avikelser': 0, 'color': null},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1455451264483, 'daysPassed': 17, 'avikelser': 0, 'color': null},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1675451264483, 'daysPassed': 17, 'avikelser': 0, 'color': null},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1575451264483, 'daysPassed': 17, 'avikelser': 27, 'color': 'bg-danger'},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utförare utsedd, inväntar handlingar i ärendet', 'startDate': 1125451264483, 'daysPassed': 17, 'avikelser': 51, 'color': 'bg-danger'},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1455451264483, 'daysPassed': 17, 'avikelser': 0, 'color': null},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1225451264483, 'daysPassed': 17, 'avikelser': 36, 'color': 'bg-danger'},
                            {'arendeId': 123453621, 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'status': 'Utredning påbörjat', 'startDate': 1335451264483, 'daysPassed': 17, 'avikelser': 0, 'color': null}
                        ];
                    }, function (err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
                }
            };
        }])

    .factory('OverviewCompletedService', ['$q', '$http', 'RESTURL',
        function ($q, $http) {//, RESTURL) {
            return {
                getEavrops: function () {
                    return $http.get('/'/*RESTURL.eavrop*/).then(function () {//data) {
                        // Success
                        //return data.data;

                        // utredare ska bort
                        return [
                            {'arendeId': 12462745, 'utredningType': 'AFU', 'totalDaysPassed': 20, 'totalCompletionDays': 0, 'avikelser': 0, 'UtredareOrganisation': 'Danderyds sjukhus', 'utredareAnsvarig': 'Assar Sverin', 'isCompleted': 'ja', 'approvedDate': 1335451264483, 'color': 'bg-success'}
                        ];
                    }, function (err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
                }
            };
        }])


    .factory('DatePickerService', ['$filter',
        function ($filter) {
            var service = {};
            service.dateFormat = 'dd-MM-yyyy';

            service.calculateInitialDateRange = function (dataList, dateSortKey) {
                if (dataList.length > 0) {
                    if (dataList.length == 1) {
                        this.update(_.first(dataList[dateSortKey], _.first(dataList)[dateSortKey]));
                    } else {
                        var ordered = $filter('orderBy')(dataList, dateSortKey, false);
                        this.update(_.first(ordered)[dateSortKey], _.last(ordered)[dateSortKey]);
                    }
                }
            };

            // Clear date selection
            service.clearStartDate = function () {
                this.startDate = null;
            };

            service.clearEndDate = function () {
                this.endDate = null;
            };

            // Disable weekend selection
            service.disabled = function (date, mode) {
                return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
            };

            service.openStart = function ($event) {
                $event.preventDefault();
                $event.stopPropagation();

                this.startDateOpened = {
                    value: true
                };
                this.endDateOpened = {
                    value: false
                };

            };

            service.openEnd = function ($event) {
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
            service.update = function (date1, date2) {
                this.startDate = date1;
                this.endDate = date2;
            };

            service.getFormattedDate = function (date) {
                return $filter('date')(date, 'dd-MM-yyyy');
            };

            return service;
        }])

    .factory('TableService', ['ngTableParams', '$filter',
        function (ngTableParams, $filter) {
            var service = {};

            // Set unfiltered data which sorting and filtering will be based on
            service.setUnfilteredData = function (data) {
                this.unfilteredData = data;
            };

            // Set headers groups
            service.setHeaderGroups = function (groups) {
                this.headerGroups = groups;
            };

            // Set headers name mapping
            service.setHeadersNameMapping = function (mapping) {
                this.headersMapping = mapping;
            };

            service.applyDateFilter = function (dateKey, startDate, endDate) {
                if (this.unfilteredData.length > 1) {
                    this.filteredData = $filter('dateFilter')(this.unfilteredData, dateKey, startDate, endDate);
                } else {
                    this.filteredData = this.unfilteredData;
                }
            };

            service.setFooterHintCircles = function (hints) {
                this.tableParams.footerHints = hints;
            };

            service.sort = function (key) {
                var params = {};
                params[key] = this.tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc';
                this.tableParams.sorting(params);
            };

            service.doDateFilter = function (dateKey, startDate, endDate) {
                this.applyDateFilter(dateKey, startDate, endDate);
                this.tableParams.reload();
            };


            service.initTableParameters = function () {
                var self = this;
                /* jshint -W055 */ // XXX: ngTableParams.
                self.tableParams = new ngTableParams({
                    page: 1,            // show first page
                    count: 10          // count per page
                }, {
                    total: self.filteredData.length, // length of data
                    getData: function ($defer, params) {
                        if (self.filteredData.length > 1) {
                            self.filteredData = params.sorting() ?
                                $filter('orderBy')(self.filteredData, params.orderBy()) :
                                self.filteredData;
                            params.total(self.filteredData.length);
                            $defer.resolve(self.filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                        } else {
                            params.total(self.filteredData.length);
                            $defer.resolve(self.filteredData);
                        }
                    }
                });
                /* jshint +W055 */
            };

            service.clearData = function(){
                this.unfilteredData = [];
                this.filteredData = [];
                this.headerGroups = [];
            };

            return service;
        }]);
