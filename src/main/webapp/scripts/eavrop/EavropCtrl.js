'use strict';

angular.module('fmuClientApp').
controller('EavropCtrl', ['$scope', 'currentEavrop', 'AuthService', 'patientInfo','EavropService',
    function($scope, currentEavrop, AuthService, patientInfo, EavropService) {
        $scope.patientInfo = patientInfo;
        $scope.currentEavrop = currentEavrop;
        $scope.getStatus = function(status) {
            return EavropService.getEavropConstants.statusMapping[status];
        };
    }
]);