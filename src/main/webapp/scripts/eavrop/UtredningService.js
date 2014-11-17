'use strict';

angular.module('fmuClientApp').factory('UtredningService', ['$http', '$q', 'RestUrlBuilderService',
    function ($http, $q, RestUrlBuilderService) {
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

            postTolkStatusChanges: function (bookningsId, comments) {
                return $http.post(RestUrlBuilderService.postTolkStatusRestUrl(bookningsId, comments))
                    .then(function (success) {
                        // Success
                        return success.data;
                    }, function (error) {
                        // Error
                        return $q.reject(error.data);
                    });
            },

            postHandelseStatusChanges: function (bookningsId, comments) {
                return $http.post(RestUrlBuilderService.postHandelseStatusRestUrl(bookningsId, comments))
                    .then(function (success) {
                        // Success
                        return success.data;
                    }, function (error) {
                        // Error
                        return $q.reject(error.data);
                    });
            }
        }
    }]);