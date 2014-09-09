'use strict';

describe('Filters Tests ', function () {
    var $filter;

    beforeEach(function () {
        module('fmuApp');

        inject(function (_$filter_) {
            $filter = _$filter_;
        });
    });
    
    it("should have a page count filter", function($filter){
        expect($filter('pageCount')).not.toBeNull() ;
    });

    it("should output [10] when totalItems is less than 10", function(){
        var input = [];
        var totalItems = 6;
        var result = $filter('pageCount')(input, totalItems);

        expect(result).toEqual([10]);
    });

    it("should output [10, 20, 50, 100, 150] when total items are 150", function(){
        var input = [];
        var totalItems = 150;
        var result = $filter('pageCount')(input, totalItems);

        expect(result).toEqual([10, 20, 50, 100, 150]);
    });
});
