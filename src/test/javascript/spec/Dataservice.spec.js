(function() {
    'use strict';

    describe('Dataservice test', function() {
        var ds, httpbackend;
        beforeEach(function() {
            module('RestModule');
            inject(function(Dataservice, $httpBackend) {
                ds = Dataservice;
                httpbackend = $httpBackend;
            });
        });

        it('should define RESTURL and dataservice', function() {
            expect(ds).toBeDefined();
        });

        it("should get all incoming-eavrops", function() {
        	var fromdate = new Date("October 13, 2014 11:13:00");
        	var todate = new Date("October 13, 2015 11:13:00");
        	var status, page, pagesize, sortkey, sortorder;

        	var eavrops = ds.getIncomingEavrop(fromdate, todate, status, page, pagesize, sortkey, sortorder);
        	expect(eavrops).toBeDefined();
        });
    });

})();