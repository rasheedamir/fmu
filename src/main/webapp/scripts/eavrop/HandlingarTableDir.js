'use strict';
angular.module('fmuClientApp')
.directive('fmuHandlingarTable', function(){
    return {
        scope: {
            handlingar: '=handlingar'
        },
        restrict: 'E',
        template:'<table class="fmu-table"> ' +
            '<thead> ' +
                '<tr> ' +
                    '<th>Handling</th> ' +
                    '<th>Registrerad av</th> ' +
                    '<th>Registrerad, datum</th> ' +
                    '</tr> ' +
            '</thead> ' +
            '<tbody> ' +
                '<tr ng-if="!handlingar || handlingar.length == 0">' +
                    '<td ng-repeat="i in [1, 2, 3]">&nbsp;</td>' +
                '</tr>'+

                '<tr ng-repeat="doc in handlingar"> ' +
                    '<td>{{doc.name}}</td> ' +
                    '<td>{{doc.regBy}}</td> ' +
                    '<td>{{doc.regDate | date:\'yyyy-MM-dd\'}}</td> ' +
                    '</tr> ' +
            '</tbody> ' +
        '</table> '
    };
})
.directive('fmuTillaggTable', function(){
    return {
        scope: {
            tillagg: '=tillagg'
        },
        restrict: 'E',
        template:
            '<table class="fmu-table"> ' +
                '<thead> ' +
                    '<tr> ' +
                        '<th>Handling</th> ' +
                        '<th>Begäran skickad, av</th> ' +
                        '<th>Begäran skickad, datum</th> ' +
                        '<th>Kommentar</th> ' +
                        '<th>Begäran skickad till</th> ' +
                    '</tr> ' +
                '</thead> ' +
                '<tbody> ' +
                    '<tr ng-if="!tillagg || tillagg.length == 0">' +
                        '<td ng-repeat="i in [1, 2, 3, 4, 5]">&nbsp;</td>' +
                    '</tr>'+

                    '<tr ng-repeat="am in tillagg"> ' +
                        '<td>{{am.name}}</td> ' +
                        '<td>{{am.sentBy}}</td> ' +
                        '<td>{{am.sentDate | date: \'yyyy-MM-dd\'}}</td> ' +
                        '<td>{{am.comment}}</td> ' +
                        '<td>{{am.sentTo}}</td> ' +
                    '</tr> ' +
                '</tbody> ' +
            '</table>'
    };
})
.directive('fmuAnteckningarTable', function($filter){
    return {
        scope: {
            anteckningar: '=',
            rowRemovable: '=?',
            removeFunc: '&'
        },
        restrict: 'E',
        controller: function ($scope) {
            $scope.toYYMMDD = function (date) {
                return $filter('date')(date, 'yyyy-MM-dd');
            };
        },

        link: function (scope) {
            scope.remove = function (note) {
                return scope.rowRemovable && scope.removeFunc() ? scope.removeFunc()(note) : null;
            };
        },
        template:
            '<table class="fmu-table greyed-hover"> ' +
            '<thead> ' +
                '<tr> ' +
                        '<th> Innehåll </th> ' +
                        '<th> Skapad av </th> ' +
                        '<th> Datum </th> ' +
                        '<th ng-if="rowRemovable"> Tabort </th> ' +
                '</tr> ' +
            '</thead> ' +
            '<tbody> ' +
                '<tr class="blank-row" ng-if="!anteckningar || anteckningar.length == 0">' +
                    '<td ng-repeat="i in [1, 2, 3]">&nbsp;</td>' +
                    '<td ng-if="rowRemovable">&nbsp;</td>' +
                '</tr>'+
                '<tr ng-repeat="note in anteckningar"> ' +
                    '<td class="text-capitalize">{{note.contents}}</td> ' +
                    '<td class="text-capitalize">{{note.createdBy}}</td> ' +
                    '<td>{{toYYMMDD(note.date)}}</td> ' +
                    '<td align="middle" ng-if="rowRemovable"> ' +
                        '<span ng-if="note.removable" class="btn btn-danger btn-sm" ng-click="remove(note)">' +
                        '<i class="glyphicon glyphicon-remove"></i>' +
                        '</span> ' +
                    '</td> ' +

                '</tr> ' +
            '</tbody> ' +
        '</table>'
    };
});
