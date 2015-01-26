'use strict';

angular.module('fmuClientApp').factory('EavropService', ['$q', '$http', 'RestUrlBuilderService',
    function($q, $http, RestUrlBuilderService) {
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
                UNASSIGNED: 'Förfrågan om utredning har inkommit',
                ASSIGNED: 'Förfrågan tilldelas, inväntar acceptans',
                ACCEPTED: 'Förfrågan accepterade',
                ON_HOLD: 'Inväntar beslut från beställare',
                SENT: 'Inväntar acceptans',
                APPROVED: 'Utredningen godkänts av beställare',
                CLOSED: 'Utredningen är avslutad',
                ONGOING: 'Utredningen är påbörjad'
            },
            isCompletedMapping: {
                true: 'Ja',
                false: 'Nej'
            },

            isCompensationApprovedMapping: {
                null: 'inväntar',
                true: 'Ja',
                false: 'Nej'
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