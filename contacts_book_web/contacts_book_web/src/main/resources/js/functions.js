var errContainer;
var login;
var pass;
var ip;

var addError = function(errMsg) {
    var html = "<div class=\"alert alert-danger\">" +
               "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>" +
               "<strong>Danger!</strong> " + errMsg +
               "</div>";
    $(errContainer).append(html);
}

var reload = function() {
    contactView.actionJS("list", null);
    groupView.actionJS("list", null);
}

var loggedIn = function(l, p, i) {
    login = l;
    pass = p;
    ip = i;

    contactView.setUser(login, pass, ip);
    groupView.setUser(login, pass, ip);

    $('#myModal').modal('hide');
    contactView.actionJS("list", null);
    groupView.actionJS("list", null);
}

var userLogin = function() {
    var login = $('#login_input_field').val();
    var pass = $('#pass_input_field').val();
    errContainer = '#login_container';

    userView.actionJS("login", login + "/" + pass);
}

var addElem = function(xpathContainer, htmlElem) {
   $(xpathContainer).append(htmlElem);
};
var remElem = function(xpathContainer, elemId) {
   $('#' + elemId).remove();
};

var onEdit = function(element) {
    element.setAttribute("value", element.value);
}

var createContact = function(name) {
    post("/contacts_book_web/contacts", {action: 'create', type: 'contact', c_name: name});
}

var createGroup = function(name) {
    post("/contacts_book_web/groups", {action: 'create', type: 'group', g_name: name});
}

var modifyContact = function(name) {
    post("/contacts_book_web/contacts", {
        action: 'modify',
        type: 'contact',
        c_name: name,
        c_email: $('#email_contact_' + name).val(),
        c_num: $('#num_contact_' + name).val(),
        c_telegram: $('#telegram_contact_' + name).val(),
        c_skype: $('#skype_contact_' + name).val(),
        g_name: $('#gName_contact_' + name).val()==''?null:$('#gName_contact_' + name).val()
    });
}

var modifyGroup = function(name) {
    post("/contacts_book_web/groups", {
            action: 'modify',
            type: 'group',
            g_name: name,
            g_color: $('#color_group_' + name).val()
        });
}

var remContact = function(name) {
    post("/contacts_book_web/contacts", {action: 'delete', type: 'contact', c_name: name});
}

var remGroup = function(name) {
    post("/contacts_book_web/groups", {action: 'delete', type: 'group', g_name: name});
}

var post = function(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}

$(function(){
});