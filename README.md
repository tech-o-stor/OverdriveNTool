# OverdriveNTool
OverdriveNTool - tool for AMD Hawaii, Fiji, Polaris, Vega GPUs
------------------------------------------------------------------
![image](https://user-images.githubusercontent.com/122867950/212854094-d495459b-5b7d-4d1b-831e-ab7a0fb0df6d.png)

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
