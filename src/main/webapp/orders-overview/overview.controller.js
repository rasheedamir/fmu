(function() {
    'use strict';

    angular.module('fmu.overview')
        .controller('OverviewController', ['$scope', function($scope) {
                $scope.select = function(option) {
                    $scope.$state.go(option.state);
                };
            }
        ]);

})();