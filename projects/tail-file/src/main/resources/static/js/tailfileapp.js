function CircularBuffer(size) {
    this.size = size;
    this.arr = [];
}

CircularBuffer.prototype.size = function() {
    return this.size;
};

CircularBuffer.prototype.add = function(a) {
    if (this.arr.length >= this.size) {
        this.arr.shift();
    }
    this.arr.push(a);
}

CircularBuffer.prototype.addArray = function(arr) {
    for (var i=0;i<arr.length;i++) {
        this.add(arr[i]);
    }
};

CircularBuffer.prototype.newlineStr = function() {
    var str="";
    for (var i=0;i<this.arr.length;i++) {
        str = str + this.arr[i] + "\n";
    }
    return str;
};

CircularBuffer.prototype.getArr = function() {
    return this.arr;
};

var tailFilesApp = angular.module("tailFilesApp",[]);

tailFilesApp.controller("TailFilesCtrl", function ($scope) {
    function init() {
        $scope.buffer = new CircularBuffer(20);
    }

    $scope.initSockets = function() {
        $scope.socket={};
        $scope.socket.client = new SockJS(URLS.tailFilesURL);  //new WebSocket('ws://' + window.location.host + URLS.tailFilesURL);
        $scope.socket.stomp = Stomp.over($scope.socket.client);
        $scope.socket.stomp.connect({}, function() {
            $scope.socket.stomp.subscribe(URLS.tailFilesTopic, $scope.notify);
        });
        $scope.socket.client.onclose = $scope.reconnect;
    };

    $scope.notify = function(message) {
        $scope.$apply(function() {
            $scope.buffer.add(angular.fromJson(message.body));
        });
    };

    $scope.reconnect = function() {
        setTimeout($scope.initSockets, 10000);
    };

    init();
    $scope.initSockets();
});