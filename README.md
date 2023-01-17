# OverdriveNTool
OverdriveNTool - tool for AMD Hawaii, Fiji, Polaris, Vega GPUs
------------------------------------------------------------------
![image](https://user-images.githubusercontent.com/122867950/212854094-d495459b-5b7d-4d1b-831e-ab7a0fb0df6d.png)

[Download OverdriveNTool](https://github.com/tech-o-stor/OverdriveNTool/)
--------------------------------------------------------------------------------------

Hi all
This application is for editing some parameters in the AMD OverdriveN API supported GPUs (currently 290, 290x, 380, 380x, 390, 390x, Fury, Fury X, Nano, 4xx, 5xx series, Vega 56, Vega 64) and Overdrive8 API supported GPUs (currently Radeon VII, RX 5000 series)
I've made this because WattTool has stopped working since driver 17.7.2.

## Additional info:
+ Workaround for bug in 17.7.2 drivers, when driver sometimes uses default voltages instead of user settings: use reset and re-apply profile.
+ It's possible to disable/enable each P state. To do this click on P0, P1.. etc. label. If P state is disabled it will not be used by GPU.
+ I2C currently supports: IR3567B (RX470, RX480, some RX5xx), up9505 (MSI RX5xx)
+ If you prefer to not touch fan settings it's possible to deactivate Fan section for each GPU. To do this press Ctrl + double click somewhere on the Fan box. It's saved per gpu_id, so GUI or commandline will not touch fan settings for such GPU.
+ To open Settings or SoftPowerPlayTable editor left click on top-left program icon, or right click on the titlebar.
+ To change "friendly name" first enable it in settings, than right click on the gpu list to open menu

## Requirements:
+ System: Windows 7 or newer
+ GPU: AMD 290, 290x, 380, 380x, 390, 390x, Fury, Fury X, Nano, 4xx, 5xx series, Vega 56, Vega 64, Radeon VII, RX 5000 series
+ Driver: 17.7.2 or newer

## Command Line:
-p[gpu_id]"Name"
apply profile "Name" to GPU with id=[gpu_id]
-c[gpu_id]"Name"
same as above, but with confirmation message that application started and everything went ok.
-r[gpu_id]
reset GPU with id=[gpu_id]
cp[gpu_id]"Name"
compare current values of GPU with id=[gpu_id] with profile "Name", and eventually set this profile if not equal
cm[gpu_id]"Name"
compare current values of GPU with id=[gpu_id] with profile "Name", and eventually set this profile if not equal, with additional message if not equal found
co[gpu_id]"Name"
only compare current values of GPU with id=[gpu_id] with profile "Name", with message if not equal found
-consoleonly
displays all messages (eg. errors) in cmd.exe console window, instead of gui messages. Only commands that are put after -consoleonly are affected, example:
"OverdriveNTool.exe" -consoleonly -r0 -p0"1" -r1 -p1"1" -r2 -p2"2" - will affect all commands
"OverdriveNTool.exe" -r0 -p0"1" -r1 -consoleonly -p1"1" -r2 -p2"2" - will affect -p1"1" -r2 -p2"2" commands only
-showgui
when added to commandline normal GUI will be displayed after program finishes with all other commands
-wait[miliseconds]
program waits specified time before parsing next command, useful if you want to add some delay for example on windows startup, can be used multiple times in one commandline
example: OverdriveNTool.exe -wait3000 -r0 -wait500 -p0profile1

[gpu_id] - it's the first number taken from GPU description, for single video card it's 0
"Name" - name of the profile that was saved ealier, must be quoted if has spaces inside

### example:
OverdriveNTool.exe -p0myProfile -p1"Profile 2"
In this example application starts without gui, then sets "myProfile" to GPU with id=0 and "Profile 2" to GPU with id=1 and then exit.

### commands can be used all together, for example:
OverdriveNTool.exe -p0myProfile -r0 co1"Profile 1"

On configs with more than 10 GPUs [gpu_id] must have 2 digits, for GPUs 0-9 leading 0 must be added, example: 00,01,02,03,04,05,06,07,08,09,10,11,12. Usage example: -p05"Name"

It's possible to use * as [gpu_id], which means it affects all supported GPUs, example:
-r* -p*MyProfile -p2"Custom profile" cm*MyProfile

## Advanced:

-ac[gpu_id] GPU_P[num]=[value];[value][;0] Mem_P[num]=[value];[value][;0] Fan_Min=[value] Fan_Max=[value] Fan_Target=[value] Fan_Acoustic=[value] Power_Temp=[value] Power_Target=[value]
-ac is similar to -p command, applies values but without using profiles. Format is identical to ini profile. All not specified values will remain untouched. Can be used with other commands (-consoleonly, * as [gpu_id], -r, -p... etc.)
[num] - Pstate number, using # as [num] will apply to highest available Pstate for scpecified GPU
[value] – value, for GPU and memory PState first value is clock (MHz), second is voltage (mV), third optional ;0 makes this Pstate disabled.
Putting * as Memory or GPU value will skip applying this value, for example:
-ac0 GPU_P7=*;800 Mem_P3=1000;* (only applies GPU_P7 voltage=800mV and Memory P3 clock=1000MHZ)
-ac0 GPU_P4=*;*;0 (only disables GPU_P4 without changing it’s values)

For I2C settings use –ac with Offset=[value] LLC=[value] PhaseGain=[value] CurrentScale=[value]
I2C must be enabled and supported for specified GPU, otherwise it will not work. Offset value is multiplied by 6,25mV, so 10 = +62,5mV, -5 = -31,25mV
Example:
-ac0 Offset=10 LLC=0
-ac0 Offset=-5 LLC=1 PhaseGain=000000 CurrentScale=60

## Commands example:

-ac0 GPU_P7=1200;800 Mem_P2=1000;850 Fan_Min=1080 Fan_Max=1700 Fan_Target=70 Fan_Acoustic=700 Power_Temp=90 Power_Target=50 -ac1 GPU_P#=1200;800 Mem_P#=1000;850
-ac0 GPU_P7=1200;800;0 Mem_P3=1000;850 Fan_Min=1080 Fan_Max=1700
-consoleonly -r5 -ac5 GPU_P#=1200;800 Mem_P#=1000;850 Fan_Min=1080 Fan_Max=1700 -ac4 Fan_Target=70 Fan_Acoustic=700 Power_Temp=90 Power_Target=50
-ac* Power_Target=-1 GPU_P7=*;*;0
-wait1000 -r0 -ac0 GPU_P7=1200;800 Mem_P1=700;850;0 Mem_P2=750;850;0 Mem_P3=800;850;0 Mem_P4=1000;850 Fan_Min=1080 Fan_Max=1700 Fan_Target=70 Fan_Acoustic=700 Power_Temp=90 Power_Target=50
-ac0 Power_Target=50 -ac1 Power_Target=-50 -ac2 Fan_Max=1700 Fan_Min=1080 -ac1 Fan_Acoustic=700
-getcurrent - prints current values for all supported GPUs in cmd console window.
-t[gpu_id]
+ restart GPU with id=[gpu_id]. It's similar to devices manager enable/disable GPU. Useful for immediately apply registry changes done to AMD keys like SoftPowerPlay table. It requires admin rigths to work.

## Changelog:

0.2.9 (14.06.2020)
+ fixed: Application recognizes driver as not installed in 20.5.1 (or newer)
+ added profiles reordering

0.2.8 (16.04.2019)
+ support for Fan Curve and Memory timing level introduced in 18.12.2 driver (Removed Fan Min, Fan Max, Fan Target Temp and Power Temp values)
+ added -wait command
+ added possibility to apply or reset all supported gpus at once by using Ctrl + Apply or Ctrl + Reset
+ fixed bug with GPUs and profiles duplication when use -showgui command
+ added support for Radeon VII

0.2.7 (09.11.2018)
+ fixed: console messages may not be displayed on Windows 10
+ added possibility to open .reg files with SoftPowerPlayTable editor
+ added optional auto reset before apply
+ added -t and -showgui commands
+ added optional displaying an error when values are not as expected after apply
+ for errors with ErrorCode -1 on apply now current driver limits are displayed

0.2.6 (16.05.2018)
+ added ini file backup feature
+ added an option to hide ADL_ERR_NOT_SUPPORTED errors
+ fixed: console messages may not be displayed on some systems
+ added -getcurrent and -ac command to apply values without using profile

0.2.5 (13.02.2018)
+ -r command now also resets I2C offset and LLC when I2C is enabled and supported
+ consoleonly command messages are now coloured
+ fixed: ini file may lose it's content on PC crash

0.2.4 (18.01.2018)
+ fixed bug in QEMU PCI passthrough with showing only one GPU on multiGPU configs
+ updated error messages to give more info

0.2.3 (14.12.2017)
+ added possibility to use * as gpu_id in commandline

0.2.2 (23.11.2017)
+ added Friendly name and Registry key to gpu additional info
+ SoftPowerPlayTable editor can now automatically restart GPU when click "Save" or "Delete"

0.2.1 (19.10.2017)
+ added SoftPowerPlayTable editor
+ commandline fix to avoid error messages when driver is not installed
+ added option to not include unsupported GPUs on the GPU list

0.2.0 (02.10.2017)
+ added possibility to deactivate Fan section

0.1.9 (25.09.2017)
+ added -consoleonly command in commandline
+ added I2C support for up9505
+ added Adapter index to gpu additional info
+ GPU displayed on startup changed to first supported rather than first one
+ Vega FE support in Pro mode
+ fixed ini bug with random values for I2C when new profile is created

0.1.8 (08.09.2017)
+ fixed dpi bug introduced in 0.1.7
= added I2C support for IR3567B

0.1.7 (26.08.2017)
+ Now additional info about GPU is optional (system menu->settings)
+ changed Pstate disabling/enabling to single click
+ commandline support for more than 10 GPUs
+ Power Target now allows negative values

0.1.6 (19.08.2017)
+ changed GPU list sorting to be like in other apps, for easier gpu recognition.

0.1.5 (18.08.2017):
+ changed tab order for edit controls
+ fixed bug with not listing all GPU's on some configs

0.1.4 (17.08.2017):
+ added possibility to disable/enable each P state.

0.1.3 (12.08.2017):
+ added few more commands

0.1.2 (08.08.2017):
+ added -c command in commandline

0.1.1 (07.08.2017):
+ prevent using commandline on unsupported cards
+ fixed bug with showing only first GPU on multiGPU configs

0.1 (06.08.2017):
+ initial release
