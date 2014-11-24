'use strict';

angular.module('fmuClientApp').factory('UtredningService', ['$http', '$filter', '$q', 'RestUrlBuilderService', 'UTREDNING',
        function ($http, $filter, $q, RestUrlBuilderService, UTREDNING) {
            function getTimeHHMM(hour, minut) {
                var hh = hour < 10 ? '0' + hour : hour;
                var mm = minut < 10 ? '0' + minut : minut;

                return hh + ' : ' + mm;
            }

            return {
                getAllEvents: function (eavropId) {
                    return $http.get(RestUrlBuilderService.buildEventsRestUrl(eavropId)
                    ).then(function (data) {
                            // Success
                            return data.data;
                        }, function (err) {
                            // Failed to retrieve data
                            return $q.reject(err.data);
                        });
                },

                createBooking: function (booking) {
                    return $http.post(RestUrlBuilderService.buildCreateBookingRestUrl(), booking)
                        .then(function (data) {
                            // Success
                            return data.data;
                        }, function (err) {
                            // Failed to retrieve data
                            return $q.reject(err.data);
                        });
                },

                changeBooking: function (dataPackage) {
                    return $http.post(RestUrlBuilderService.changeBookingRestUrl(), dataPackage)
                        .then(function (success) {
                            // Success
                            return success.data;
                        }, function (error) {
                            // Error
                            return $q.reject(error.data);
                        });
                },

                changeTolkBooking: function (dataPackage) {
                    return $http.post(RestUrlBuilderService.changeTolkBookingRestUrl(), dataPackage)
                        .then(function (success) {
                            // Success
                            return success.data;
                        }, function (error) {
                            // Error
                            return $q.reject(error.data);
                        });
                },

                getTableFields: function () {
                    return [
                        {
                            key: 'handelse',
                            name: 'Händelse',
                            restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                        },
                        {
                            key: 'dateOfEvent',
                            name: 'Datum',
                            restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                        },
                        {
                            key: 'timeOfEvent',
                            name: 'Tidpunkt',
                            restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                        },
                        {
                            key: 'utredaPerson',
                            name: 'Person',
                            restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                        },
                        {
                            key: 'role',
                            name: 'Roll',
                            restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                        },

                        {
                            key: 'tolkStatus',
                            name: 'Tolk',
                            restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                        },
                        {
                            key: 'handelseStatus',
                            name: 'Status',
                            restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                        }
                    ];
                },

                getTableCellValue: function (key, rowData) {
                    var celldata = rowData[key];
                    if (celldata === null) {
                        return '-';
                    }
                    switch (key) {
                        case 'timeOfEvent' :
                            var endTime = rowData.timeOfEventEnd;
                            if (endTime !== null) {
                                return getTimeHHMM(celldata.hour, celldata.minute) + ' - ' + getTimeHHMM(endTime.hour, endTime.minute);
                            }

                            return getTimeHHMM(celldata.hour, celldata.minute);
                        case 'dateOfEvent':
                            return $filter('date')(celldata, UTREDNING.dateFormat);
                        case 'tolkStatus' :
                            if (celldata.currentStatus === null) {
                                return '-';
                            }
                            return UTREDNING.tolkMapping[celldata.currentStatus.name];
                        case 'handelseStatus' :
                            if (celldata.currentStatus === null) {
                                return '-';
                            }
                            return UTREDNING.handelseMapping[celldata.currentStatus.name];
                        case 'handelse':
                            return UTREDNING.statusMapping[celldata];
                        default :
                            return celldata;
                    }
                }
            };
        }]
);