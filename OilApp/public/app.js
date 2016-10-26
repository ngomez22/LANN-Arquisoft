/**
 * Created by Nicolás on 25/10/16.
 */
(function (ng) {

    var mod = ng.module("mainApp", [
        "ui.bootstrap",
        "aboutModule"
        //MODULOS
    ]);

    mod.config(['$logProvider', function ($logProvider) {
        $logProvider.debugEnabled(true);
    }]);

    mod.config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('/about', {
                url: '/about',
                controller: 'AboutController',
                controllerAs: "ctrl"
            })
    }]);
})(window.angular);