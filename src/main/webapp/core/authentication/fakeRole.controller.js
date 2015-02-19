'use strict';
angular.module('fmu.core')
.controller('FakeRoleCtrl', ['$scope','AuthService', function($scope, AuthService){

    $scope.roles = ['ROLE_SAMORDNARE', 'ROLE_UTREDARE'];
    $scope.hasRole = AuthService.hasRole;
    $scope.changeRole = AuthService.changeRole;
}]);
