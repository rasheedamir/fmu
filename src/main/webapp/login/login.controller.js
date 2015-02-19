(function() {
    'use strict';
    angular.module('fmu.login')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', 'Dataservice'];

    function LoginController($scope, Dataservice) {
        $scope.loginArr = [{
            'fornamn': 'Åsa',
            'efternamn': 'Andersson',
            'hsaId': 'IFV1239877878-104B',
            'enhetId': 'IFV1239877878-1045',
            'lakare': true
        }, {
            'fornamn': 'Sam',
            'efternamn': 'Ordnarsson',
            'hsaId': 'IFV1239877878-104K',
            'enhetId': 'IFV1239877878-1045',
            'lakare': true
        }];

        $scope.login = login;

        init();


        function init() {
        	$scope.selectedItem = $scope.loginArr[0];
        }

        function login() {
            Dataservice.login($.param(($scope.selectedItem)));
        }
    }
})();