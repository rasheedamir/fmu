'use strict';
describe("log test", function() {
    var logservice, log;
    beforeEach(module('util.logger'));
    beforeEach(inject(function(logger, $log) {
        logservice = logger;
        log = $log;
    }));

    it("should create a logger instance", function() {
        expect(log).toBeDefined();
    });

    it("should log info message", function() {
    	var message = 'myinfo';
    	var data = 'data';

    	logservice.info(message, data);
    	expect(log.info.logs).toContain(['Info: ' + message, data]);
    });

    it("should log warn message", function() {
    	var message = 'mywarn';
    	var data = 'data';

    	logservice.warning(message, data);
    	expect(log.warn.logs).toContain(['Warning: ' + message, data]);
    });

    it("should log error message", function() {
    	var message = 'myerror';
    	var data = 'data';

    	logservice.error(message, data);
    	expect(log.error.logs).toContain(['Error: ' + message, data]);
    });

    it("should log success message", function() {
    	var message = 'mysuccess';
    	var data = 'data';

    	logservice.success(message, data);
    	expect(log.info.logs).toContain(['Success: ' + message, data]);
    });
});