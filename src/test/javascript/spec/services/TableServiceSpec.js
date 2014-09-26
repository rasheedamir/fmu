'use strict';


describe('TableService Tests ', function () {
    var tableService, scope;
    var date1 = new Date('2011-01-11').getTime();
    var date2 = new Date('2011-02-11').getTime();
    var date3 = new Date('2011-03-11').getTime();
    var date4 = new Date('2011-10-11').getTime();
    var dateKey = 'creationTime';
    var data = [
    {'creationTime': date1},
    {'creationTime': date2},
    {'creationTime': date3},
    {'creationTime': date4}
    ];

    beforeEach(module('fmuClientApp'));
    beforeEach(inject(function ($rootScope, TableService) {
        tableService = TableService;
        scope = $rootScope.$new();
        scope.tableData = data;
        scope.dateKey = dateKey;
        tableService.setScope(scope);
    }));

    it('should define a tableService', function () {
        expect(tableService).toBeDefined();
        expect(tableService.scope).toBe(scope);
    });

    it('should return filtered data', function () {
        scope.startDate = date1;
        scope.endDate = date2;
        var result = tableService.getDateFilteredData();
        expect(result.length).toBe(2);
        expect(_.difference(result, _.intersection(result, data)).length).toBe(0);
    });

    it('should define tablular paramters', function () {
        scope.startDate = date1;
        scope.endDate = date4;
        tableService.initTableParameters();
        expect(scope.tableParams).toBeDefined();
        expect(scope.tableParams.count()).toEqual(10);
        expect(scope.tableParams.settings().$scope).toBe(scope);
        expect(scope.tableParams.total()).toBe(4);
    });

    it('should handle data with only one element', function(){
        var oneRowData = [data[0]];
        scope.tableData = oneRowData;
        scope.startDate = date1;
        scope.endDate = date1;
        tableService.initTableParameters();
        expect(tableService.getDateFilteredData()).toBe(oneRowData);
        expect(scope.tableParams.total()).toBe(1);
        console.log(scope.tableParams.data);
    });

    /*




    it('should filter out data not in range', function () {
        tableService.setUnfilteredData(data);
        tableService.applyDateFilter(dateKey, date2, date4);
        expect(_.difference(_.rest(data), tableService.filteredData).length).toBe(0);
    });

    it('should return empty array if all data are out of date range', function () {
        tableService.setUnfilteredData(data);
        tableService.applyDateFilter(dateKey, date4 + 1, date4 + 2);
        expect(tableService.filteredData.length).toBe(0);
    });

*/
});