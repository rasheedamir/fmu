'use strict';

describe('TableService Tests ', function () {
    var dateService, scope;
    var date1 = new Date('2011-01-11').getTime();
    var date2 = new Date('2011-02-11').getTime();
    var date3 = new Date('2011-03-11').getTime();
    var date4 = new Date('2011-10-11').getTime();
    var data = [
    {'creationTime': date1},
    {'creationTime': date2},
    {'creationTime': date3},
    {'creationTime': date4}
    ];

    var mockEvent = {
        preventDefault: function(){},
        stopPropagation: function(){}
    };

    beforeEach(module('fmuClientApp'));
    beforeEach(inject(function($rootScope, DatePickerService){
        dateService = DatePickerService;
        scope = $rootScope.$new();
        scope.tableData = data;
        scope.dateKey = 'creationTime';
        dateService.setScope(scope);
    }));

    it('should create a DatePickerService instance', function(){
        expect(dateService).toBeDefined();
    });

    it('should set the scope', function(){
        expect(dateService.scope).toBe(scope);
    });


    it('should set date range', function(){
        dateService.update(date1, date4);
        expect(dateService.scope.startDate).toBe(date1);
        expect(dateService.scope.endDate).toBe(date4);
    });

    it('should set default date format', function(){
        expect(dateService.dateFormat).toBe('dd-MM-yyyy');
    });

    it('should calculate correct date range based on input data', function(){
        dateService.calculateInitialDateRange();
        expect(scope.startDate).toBe(date1);
        expect(scope.endDate).toBe(date4);
    });

    it('should clear date', function(){
        dateService.update(date1, date4);
        expect(scope.startDate).toBe(date1);
        expect(scope.endDate).toBe(date4);

        dateService.clearStartDate();
        expect(scope.startDate).toBeNull();
        dateService.clearEndDate();
        expect(scope.endDate).toBeNull();
    });

    it('should open start date picker and close end date picker', function(){
        dateService.openStart(mockEvent);
        expect(dateService.startDateOpened.value).toBe(true);
        expect(dateService.endDateOpened.value).toBe(false);
    });

    it('should open end date picker and close start date picker', function(){
        dateService.openEnd(mockEvent);
        expect(dateService.startDateOpened.value).toBe(false);
        expect(dateService.endDateOpened.value).toBe(true);
    });

    it('should be able to convert date milis to human readable date string', function(){
        var formattedDate = dateService.getFormattedDate(date1);
        expect(formattedDate).toBe('11-01-2011');
    });
});