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
   $(xpathContainer + '>#' + elemId).remove();
};

var onEdit = function(element) {
    element.setAttribute("value", element.value);
}

var createContact = function(name) {
    var html = "<tr id=\"contact_" + name + "\">" +
               "<td><div class=\"input-group input-group-sm\" disabled>" +
               "<input id=\"pKey_contact_" + name + "\" disabled type=\"text\" class=\"form-control\" value=\"" + name + "\" placeholder=\"name\">" +
               "</div></td>" +
               "<td onclick=\"$('#controls_contact_" + name + "').removeClass('hidden')\">" +
               "<div class=\"input-group input-group-sm\">" +
               "  <input onchange=\"onEdit(document.getElementById('email_contact_" + name + "'))\" id=\"email_contact_" + name + "\" type=\"text\" class=\"form-control\" value=\"\" placeholder=\"email\">" +
               "</div></td>" +
               "<td onclick=\"$('#controls_contact_" + name + "').removeClass('hidden')\">" +
               "<div class=\"input-group input-group-sm\">" +
               "<input pattern=\"[0-9]{10}\" onchange=\"onEdit(document.getElementById('num_contact_" + name + "'))\" id=\"num_contact_" + name + "\" type=\"text\" class=\"form-control\" value=\"0\" placeholder=\"number\">" +
               "</div></td>" +
               "<td onclick=\"$('#controls_contact_" + name + "').removeClass('hidden')\">" +
               "<div class=\"input-group input-group-sm\">" +
               "<input onchange=\"onEdit(document.getElementById('skype_contact_" + name + "'))\" id=\"skype_contact_" + name + "\" type=\"text\" class=\"form-control\" value=\"\" placeholder=\"skype\">" +
               "</div></td>" +
               "<td onclick=\"$('#controls_contact_" + name + "').removeClass('hidden')\">" +
               "<div class=\"input-group input-group-sm\">" +
               "<input onchange=\"onEdit(document.getElementById('telegram_contact_" + name + "'))\" id=\"telegram_contact_" + name + "\" type=\"text\" class=\"form-control\" value=\"\" placeholder=\"telegram\">" +
               "</div></td>" +
               "<td onclick=\"$('#controls_contact_" + name + "').removeClass('hidden')\">" +
               "<div class=\"input-group input-group-sm\">" +
               "<input onchange=\"onEdit(document.getElementById('gName_contact_" + name + "'))\" id=\"gName_contact_" + name + "\" type=\"text\" class=\"form-control\" value=\"\" placeholder=\"group name\">" +
               "</div></td>"+
               "<td colspan=\"1\" class=\"hidden\" id=\"controls_contact_" + name + "\">"+
               "<div class=\"btn-group-vertical\">"+
               "<div class=\"btn-group\">"+
               "<button type=\"button\" class=\"btn btn-warning\" onclick=\"contactView.actionJS('modify', document.getElementById('contact_" + name + "').outerHTML)\">"+
               "<span class=\"glyphicon glyphicon-pencil\"></span>"+
               "</button>"+
               "</div>"+
               "<div class=\"btn-group\">"+
               "<button type=\"button\" class=\"btn btn-danger\" onclick=\"contactView.actionJS('rem', document.getElementById('contact_" + name + "').outerHTML);" +
                       "remElem('div.col-md-7.bubna-col>div.table-responsive>table.table-hover>tbody', '" + name + "');\">"+
               "<span class=\"glyphicon glyphicon-minus\"></span>"+
               "</button>"+
               "</div>"+
               "<div class=\"btn-group\">"+
               "<button type=\"button\" class=\"btn btn-info\" onclick=\"$('#controls_contact_" + name + "').addClass('hidden')\">"+
               "<span class=\"glyphicon glyphicon-chevron-right\"></span>"+
               "</button>"+
               "</div>"+
               "</div>"+
               "</td>"+
               "</tr>";

    contactView.actionJS('modify', html);
    addElem('div.col-md-7.bubna-col>div.table-responsive>table.table-hover', html);
}

var createGroup = function(name) {
    var html = "<tr id=\"group_" + name + "\">" +
               "<td><div class=\"input-group input-group-sm\" disabled>" +
               "<input id=\"pKey_group_" + name + "\" disabled type=\"text\" class=\"form-control\" value=\"" + name + "\" placeholder=\"name\">" +
               "</div></td>" +
               "<td onclick=\"$('#controls_group_" + name + "').removeClass('hidden')\">" +
               "<div class=\"input-group input-group-sm\">" +
               "  <input pattern=\"[0-9]{10}\" onchange=\"onEdit(document.getElementById('color_group_" + name + "'))\" id=\"color_group_" + name + "\" type=\"text\" class=\"form-control\" value=\"0\" placeholder=\"color\">" +
               "</div></td>" +
               "<td colspan=\"1\" class=\"hidden\" id=\"controls_group_" + name + "\">"+
               "<div class=\"btn-group-vertical\">"+
               "<div class=\"btn-group\">"+
               "<button type=\"button\" class=\"btn btn-warning\" onclick=\"groupView.actionJS('modify', document.getElementById('group_" + name + "').outerHTML)\">"+
               "<span class=\"glyphicon glyphicon-pencil\"></span>"+
               "</button>"+
               "</div>"+
               "<div class=\"btn-group\">"+
               "<button type=\"button\" class=\"btn btn-danger\" onclick=\"groupView.actionJS('rem', document.getElementById('group_" + name + "').outerHTML);" +
                       "remElem('div.col-md-7.bubna-col>div.table-responsive>table.table-hover>tbody', 'group_" + name + "');\">"+
               "<span class=\"glyphicon glyphicon-minus\"></span>"+
               "</button>"+
               "</div>"+
               "<div class=\"btn-group\">"+
               "<button type=\"button\" class=\"btn btn-info\" onclick=\"$('#controls_group_" + name + "').addClass('hidden')\">"+
               "<span class=\"glyphicon glyphicon-chevron-right\"></span>"+
               "</button>"+
               "</div>"+
               "</div>"+
               "</td>"+
               "</tr>";

    groupView.actionJS('modify', html);
    addElem('div.col-md-5.bubna-col>div.table-responsive>table.table-hover', html);
}

$(function(){
	alert("command:ready");
	$('#myModal').modal('show');
	errContainer = '#login_container';
	addError('test');
});