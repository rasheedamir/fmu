'use strict';


describe('TableService Tests ', function () {
    var tableService;
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
    beforeEach(inject(function (TableService) {
        tableService = TableService;
    }));

    it('should define a tableService', function () {
        expect(tableService).toBeDefined();
    });

    it('should set unfiltered data', function () {
        tableService.setUnfilteredData(data);
        expect(tableService.unfilteredData).toEqual([
            {'creationTime': date1},
            {'creationTime': date2},
            {'creationTime': date3},
            {'creationTime': date4}
        ]);
    });

    it('should filter and set filtered data', function () {
        tableService.setUnfilteredData(data);
        tableService.applyDateFilter(dateKey, date1, date2);
        expect(tableService.unfilteredData).toBe(data);
    });

    it('should handle data with only one element', function(){
        var oneRowData = [data[0]];
        tableService.setUnfilteredData(oneRowData);
        tableService.applyDateFilter(dateKey, date1, date1);
        expect(tableService.unfilteredData).toBe(oneRowData);
    });

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

    it('should define tablular paramters', function () {
        tableService.setUnfilteredData(data);
        tableService.applyDateFilter(dateKey, date1, date4);
        tableService.initTableParameters();
        expect(tableService.tableParams).toBeDefined();
        expect(tableService.tableParams.count()).toEqual(10);
        expect(tableService.tableParams.total()).toEqual(4);
    });
});