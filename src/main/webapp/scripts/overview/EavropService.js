'use strict';

angular.module('fmuClientApp').factory('EavropService', ['$q', '$http', 'RestUrlBuilderService', 'gettext',
    function($q, $http, RestUrlBuilderService, gettext) {
        var eavropConstants = {
            dateFormat: 'yyyy-MM-dd',
            sortKeyMap: {
                // since pagination is done in backend hibernate sort is based on eavrops field values mapping
                // is needed when going from DTO key to actual eavrop fieldkey

                arendeId: 'arendeId',
                utredningType: 'utredningType',
                status: 'eavropState'
            },
            statusMapping: {
                UNASSIGNED: gettext('Eavrop-status/Förfrågan om utredning har inkommit'),
                ASSIGNED: gettext('Eavrop-status/Förfrågan tilldelas, inväntar acceptans'),
                ACCEPTED: gettext('Eavrop-status/Förfrågan accepterade'),
                ON_HOLD: gettext('Eavrop-status/Inväntar beslut från beställare'),
                SENT: gettext('Eavrop-status/Inväntar acceptans'),
                APPROVED: gettext('Eavrop-status/Utredningen godkänts av beställare'),
                CLOSED: gettext('Eavrop-status/Utredningen är avslutad'),
                ONGOING: gettext('Eavrop-status/Utredningen är påbörjad')
            },
            isCompletedMapping: {
                true: gettext('Eavrop-tabell-komplett-status/Ja'),
                false: gettext('Eavrop-tabell-komplett-status/Nej')
            },

            isCompensationApprovedMapping: {
                null: gettext('Eavrop-tabell-kompensation-godkänd-status/inväntar'),
                true: gettext('Eavrop-tabell-kompensation-godkänd-status/Ja'),
                false: gettext('Eavrop-tabell-kompensation-godkänd-status/Nej')
            },
            eavropStatus: {
                notAccepted: 'NOT_ACCEPTED',
                accepted: 'ACCEPTED',
                completed: 'COMPLETED'
            }
        };

        return {
            getEavropConstants: eavropConstants,
            getEavrops: function(startDate, endDate, eavropStatus, currentPageNumber,
                currentNrOfElementPerPage, sortKey, sortOrder) {
                return $http.get(RestUrlBuilderService.buildOverViewRestUrl(
                    startDate,
                    endDate,
                    eavropStatus,
                    currentPageNumber,
                    currentNrOfElementPerPage,
                    sortKey,
                    sortOrder)).then(function(data) {
                    // Success
                    return data.data;
                }, function(err) {
                    // Failed to retrieve data
                    return $q.reject(err.data);
                });
            },

            addNote: function(dataPackage) {
                return $http.post(RestUrlBuilderService.buildAddNoteRestUrl(), dataPackage)
                    .then(function(data) {
                        // Success
                        return data.data;
                    }, function(err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
            },

            removeNote: function(eavropId, noteId) {
                return $http.delete(RestUrlBuilderService.buildRemoveNoteRestUrl(eavropId, noteId))
                    .then(function(data) {
                        // Success
                        return data.data;
                    }, function(err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
            },
            getCompensation: function(eavropId) {
                return $http.get(RestUrlBuilderService.buildCompensationRestUrl(eavropId))
                    .then(function(data) {
                        // Success
                        return data.data;
                    }, function(err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
            }
        };

    }
]);