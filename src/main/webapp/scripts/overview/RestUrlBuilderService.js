'use strict';

angular.module('fmuClientApp').factory('RestUrlBuilderService',
    function () {
        return {
            buildOverViewRestUrl: function (startDate, endDate, eavropStatus, currentPageNumber,
                                            currentNrOfElementPerPage, sortKey, sortOrder) {
                return '/app/rest/eavrop' +
                    '/fromdate/' + startDate +
                    '/todate/' + endDate +
                    '/status/' + eavropStatus +
                    '/page/' + currentPageNumber +
                    '/pagesize/' + currentNrOfElementPerPage +
                    '/sortkey/' + sortKey +
                    '/sortorder/' + sortOrder
            },

            buildEventsRestUrl: function (eavropId) {
                return '/app/rest/eavrop/'+ eavropId + '/utredning';
            },

            buildCreateBookingRestUrl: function () {
                return '/app/rest/eavrop/utredning/create/booking';
            }
        }
    });