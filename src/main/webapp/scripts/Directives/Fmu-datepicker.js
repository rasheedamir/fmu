'use strict';
angular.module('fmuClientApp')
    .directive('fmuDatepicker', function(AuthService){
        return {
            restrict: 'E',
            scope: {
                dateModel: '=',
                title: '@'
            },
            controller: function ($scope, $element) {
                $scope.isOpened = false;

                // Disable weekend selection
                $scope.disabled = function(date, mode) {
                    return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
                };

                $scope.open = function($event) {
                    $event.preventDefault();
                    $event.stopPropagation();

                    $scope.isOpened = !$scope.isOpened;

                };
            },
            template:
                '<div class="row"> ' +
                    '<b>{{title}}</b> ' +
                    '<div class="input-group">' +
                        '<input type="text" ' +
                            'class="form-control" ' +
                            'datepicker-popup="{{dateFormat}}" ' +
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
    });
