(function() {
    'use strict';
    angular.module('fmu.widgets')
        .directive('fmuDatepicker', fmuDatepicker);

    function fmuDatepicker() {
        return {
            restrict: 'E',
            scope: {
                dateModel: '=',
                title: '@'
            },
            controller: ['$scope', function($scope) {
                $scope.isOpened = false;
                $scope.dateOptions = {
                    'show-weeks': false
                };

                // Disable weekend selection
                $scope.disabled = function(date, mode) {
                    return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
                };

                $scope.open = function($event) {
                    $event.preventDefault();
                    $event.stopPropagation();

                    $scope.isOpened = !$scope.isOpened;
                };
            }],
            template: '<div class="form-group row"> ' +
                '<label>{{title}}</label> ' +
                '<div class="input-group">' +
                '<input type="text" ' +
                'class="form-control" ' +
                'datepicker-popup="{{dateFormat}}" ' +
                'datepicker-options="dateOptions"' +
                'ng-model="dateModel" ' +
                'is-open="isOpened" ' +
                'ng-required="true" ' +
                'close-text="Close"/> ' +
                '<span class="input-group-addon"> ' +
                '<a ng-click="open($event)">' +
                '   <i class="glyphicon glyphicon-calendar"></i>' +
                '</a> ' +
                '</span> ' +
                '</div>' +
                '</div>'
        };
    }
})();