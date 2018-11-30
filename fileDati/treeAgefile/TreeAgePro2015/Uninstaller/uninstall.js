// This script unisntalls TreeAge Pro OI DLL and Excel Add-In

var fs = WScript.CreateObject("Scripting.FileSystemObject");
var sh = WScript.CreateObject("WScript.Shell");

// Finding the Excel plug-in directory
var EXCEL_PLUGIN = "com.treeage.treeagepro.excel.win32_";
var pdir = fs.GetParentFolderName(fs.GetParentFolderName(WScript.ScriptFullName)) + "\\plugins";
var pdirs = new Enumerator(fs.GetFolder(pdir).Subfolders);
for (; !pdirs.atEnd(); pdirs.moveNext()) {
    pdir = pdirs.item();
    if (pdir.Name.slice(0, EXCEL_PLUGIN.length) == EXCEL_PLUGIN) {
        break;
    }
}

var ADDIN_FILE = "tpaddin.xla";
var OIX86_FILE = pdir.Path + "\\treeagepro.oi.dll";
var OIX64_FILE = pdir.Path + "\\treeagepro.oi.x64.dll";

// Uninstalling the Add-In
var excel = WScript.CreateObject("Excel.Application");
for (i = 1; i <= excel.AddIns.Count; i++) {
    var addin = excel.AddIns(i);
    if (addin.Name == ADDIN_FILE) {
        try {
            addin.Installed = false;
            fs.DeleteFile(addin.FullName, true);
        } catch (err) {
          // do nothing, continue with dll unregistering
        }
    }
}
excel.Quit();


// Unregistering OI DLL, x86 version is enough
if (fs.FileExists(OIX86_FILE)) {
    sh.Run("regsvr32 /u /s \"" + OIX86_FILE + "\"", 1, true);
}
