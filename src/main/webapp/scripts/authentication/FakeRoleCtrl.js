
angular.module('fmuClientApp')
.controller('FakeRoleCtrl', ['$scope','AuthService', function($scope, AuthService){


    $scope.roles = ['ROLE_SAMORDNARE', 'ROLE_UTREDARE'];
    $scope.hasRole = AuthService.hasRole;
    $scope.addRole = AuthService.addRole;
    $scope.removeRole = AuthService.removeRole;

}]);
