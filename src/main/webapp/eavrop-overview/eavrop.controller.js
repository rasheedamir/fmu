(function() {
    'use strict';

    angular.module('fmu.eavrop')
        .controller('EavropController', EavropController);

    /*@ngInject*/
    function EavropController($scope, currentEavrop, AuthService, patientInfo, eavropService) {
        $scope.patientInfo = patientInfo;
        $scope.currentEavrop = currentEavrop;

        $scope.getStatus = function(status) {
            return eavropService.getEavropConstants.statusMapping[status];
        };
    }
    EavropController.$inject = ['$scope', 'currentEavrop', 'AuthService', 'patientInfo', 'eavropService'];
})();