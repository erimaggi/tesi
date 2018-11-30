var shell = new ActiveXObject("Shell.Application");
var app = WScript.Arguments(0);

var args = "";
for (var idx = 1; idx < WScript.Arguments.Length; idx += 1) {
    args += "\"" + WScript.Arguments(idx) + "\" ";
}

shell.ShellExecute(app, args, "", "runas");