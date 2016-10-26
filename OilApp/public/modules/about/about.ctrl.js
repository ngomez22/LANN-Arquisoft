/**
 * Created by Nicolás on 25/10/16.
 */
(function (ng) {
    var mod = ng.module("aboutModule");

    mod.controller('AboutController', ['$scope', 'aboutService', function ($scope, svc) {

        var self = this;

        $scope.users = [];

        $scope.test = "hola esto es una prueba";

        $scope.createMode = false;

        $scope.style = function(b){
            if (b){
                return "timeline-inverted";
            }
            else {
                return "timeline-not-inverted";
            }
        };

        this.showError = function (data) {
            alert(data);
        };

        function responseError(response) {
            self.showError(response);
        }

        $scope.fetchUsers = function () {
            console.log("Fecthing all users");
            return svc.fetchRecords().then(function (response) {
                $scope.users = response.data;
                console.log(response.data);
                return response;
            }, responseError);
        };

        $scope.createEvent = function (){
            $scope.editMode = true;
            $scope.currentEvent = {"id": undefined};
            $scope.$broadcast("post-create", $scope.currentRecord);
        };

        $scope.saveEvent = function () {
            console.log(dataSvc.userId+', '+dataSvc.tripId+', '+dataSvc.dayId);
            var event = $scope.currentEvent;
            if (event.id || event.cid) {
                var i = indexOf(event);
                $scope.users.splice(i, 1, event);
            } else {
                var id = Math.floor(Math.random() * 10000);
                event.id = id;
                $scope.users.push(event);
            }
            $scope.editMode = false;
            var newEvents = $scope.users;
            return svc.replaceEvents(dataSvc.userId, dataSvc.tripId, dataSvc.dayId, newEvents).then(function (response) {
                $scope.users = response.data;
                console.log(response.data);
                $scope.currentEvent = {"id": undefined};
                return response;
            }, responseError);
        };

        $scope.editEvent = function (event) {
            console.log("EDIT: " + dataSvc.userId+', '+dataSvc.tripId+', '+dataSvc.dayId+', '+event.id);
            $scope.currentEvent= event;
            $scope.editMode = true;
        };

        $scope.deleteEvent = function(event) {
            console.log("DELETE: " + dataSvc.userId+', '+dataSvc.tripId+', '+dataSvc.dayId+', '+event.id);
            return svc.removeEvent(dataSvc.userId, dataSvc.tripId, dataSvc.dayId, event.id).then(function () {
                $scope.fetchEvents();
            }, responseError);
        };

        $scope.fecthUsers();
    }]);
})(window.angular);