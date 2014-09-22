'use strict';

/* Services */
angular.module('fmuClientApp').factory('EavropService', ['$q', '$http', 'RESTURL',
    function ($q, $http){//, RESTURL) {
        return {
            getEavrops: function () {
                return $http.get('/'/*RESTURL.eavrop*/).then(function (){//data) {
                    // Success
                    //return data.data;
                    return [
                        {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                        {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                        {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                        {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                        {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                        {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                        {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                        {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                        {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                        {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                        {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                        {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                        {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                        {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                        {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                        {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                        {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                        {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                        {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                        {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                        {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                        {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                        {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                        {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'}
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
                if (dataList !== null && dataList.length > 0) {
                    var ordered = $filter('orderBy')(dataList, dateSortKey, false);
                    this.update(_.first(ordered)[dateSortKey], _.last(ordered)[dateSortKey]);
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
            service.setUnfilteredData = function(data){
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
                this.filteredData = $filter('dateFilter')(this.unfilteredData, dateKey, startDate, endDate);
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
                        self.filteredData = params.sorting() ?
                            $filter('orderBy')(self.filteredData, params.orderBy()) :
                            self.filteredData;
                            params.total(self.filteredData.length);
                        $defer.resolve(self.filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                    }
                });
                /* jshint +W055 */
            };

            return service;
        }]);
