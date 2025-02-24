package main

type CommandLineArgs struct {
	args map[string]string
}

func parseCommonLineArgs(arguments []string) CommandLineArgs {
	commandLineArgs := CommandLineArgs{}
	mapping := make(map[string]string)
	for i := 1; i < len(arguments); {
		mapping[arguments[i]] = arguments[i+1]
		i += 2
	}
	commandLineArgs.args = mapping
	return commandLineArgs
}
