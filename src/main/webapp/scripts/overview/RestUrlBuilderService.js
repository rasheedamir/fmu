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
            },

            changeBookingRestUrl: function () {
                return '/app/rest/eavrop/utredning/modify/booking';
            },

            changeTolkBookingRestUrl: function () {
                return '/app/rest/eavrop/utredning/modify/tolk';
            },

            buildAddNoteRestUrl: function () {
                return '/app/rest/eavrop/note/add';
            },

            buildRemoveNoteRestUrl: function (eavropId, noteId) {
                return '/app/rest/eavrop/'+ eavropId + '/note/' + noteId + '/remove';
            }
        }
    });