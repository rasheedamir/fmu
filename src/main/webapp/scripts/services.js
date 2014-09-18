'use strict';

/* Services */
angular.module('fmuClientApp').factory('EavropService', ['$q', '$http', 'RESTURL',
    function ($q, $http, RESTURL) {
        return {
            getEavrops: function () {
                return $http.get(RESTURL.eavrop).then(function (data) {
                    // Success
                    return data.data;
                }, function (err) {
                    // Failed to retrieve data
                    return $q.reject(err.data);
                });
            }
        };
    }])

    .factory('DateSelectionChangeService', ['$filter',
        function ($filter) {
            var service = {};
            service.startDate = null;
            service.endDate = null;
            service.dateFormat = 'dd-MM-yyyy';
            service.startDate = new Date(0);
            service.endDate = new Date(0);

            service.calculateInitialDateRange = function (dataList, dateSortKey) {
                if (dataList !== null && dataList.length > 0) {
                    var ordered = $filter('orderBy')(dataList, dateSortKey, false);
                    this.setDateRange(_.first(ordered)[dateSortKey], _.last(ordered)[dateSortKey]);
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

            service.setDateRange = function (startDate, endDate) {
                this.startDate = startDate;
                this.endDate = endDate;
            };

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

            service.initTableParameters = function () {
                var self = this;
                if (!self.tableParams) {
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
                            $defer.resolve(self.filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                        }
                    });
                    /* jshint +W055 */
                }
            };

            return service;
        }]);
