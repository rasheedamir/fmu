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
            }
        }
    }]);