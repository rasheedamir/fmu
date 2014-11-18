'use strict';

angular.module('fmuClientApp').
    controller('EavropCtrl',['$scope','currentEavrop', 'AuthService', function($scope,  currentEavrop, AuthService){

    $scope.currentEavrop = currentEavrop;

    $scope.patientInfoText = function(){
        /*if(AuthService.hasRole('ROLE_UTREDARE')){
            return currentEavrop.patient.details.socSecNo + ', ' + currentEavrop.patient.details.name;
        } else {
            return currentEavrop.patient.dobYear + ', ' + currentEavrop.patient.initials;
        }*/
    }
}]);
