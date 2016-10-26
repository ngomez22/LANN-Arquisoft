/**
 * Created by Nicolás on 25/10/16.
 */
(function (ng) {

    var mod = ng.module("aboutModule");

    mod.service("aboutService", ["$http", "aboutContext", function ($http, context) {

        /**
         * Hace la petición GET necesaria para obtener los usuarios.
         * @returns {unresolved}
         */
        this.fetchRecords = function () {
            return $http.get("/usuarios");
        };

        /**
         * Hace la petición GET necesaria para obtener un usuario.
         * @param {type} idUsuario
         * @returns {unresolved}
         */
        this.fetchRecord = function (idUsuario) {
            return $http.get("/usuario/"+idUsuario);
        };

        /**
         * Se hace una petición PUT para actualizar el usuario.
         * @param {type} usuario
         * @returns {unresolved}
         */
        this.createRecord = function (usuario) {
            return $http.put("/usuario", usuario);
        }

        /**
         * Se hace un una petición DELETE para eliminar el día indicado.
         * @param {type} id
         * @returns {unresolved}
         */
        this.deleteRecord = function (idUsuario) {
            return $http.delete("/usuario/"+idUsuario);
        };

        /**
         * Se hace una petición PUT para actualizar el usuario.
         * @param {type} idUsuario
         * @param {type} usuario
         * @returns {unresolved}
         */
        this.updateRecord = function(idUsuario, usuario){
            return $http.put("/usuario/"+idUsuario, usuario);
        };
    }]);

})(window.angular);