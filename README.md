# Quotely for Gremlin

# Overview
This is the utility requested for the Sr. Software Engineering take home test,
a utility called `quotely` that utilizes the forismatic.com quote api
([http://forismatic.com/en/api/](http://forismatic.com/en/api/)).

# Description
The utility was developed as a [Maven Project](maven.org) in order to better
manage dependencies and aid in rapid development.

# Prerequisites
* Java 17+
* [Maven](maven.org)

# To Build
In the `Quotely` directory, run the following...

```shell
$ mvn compile
$ mvn package
```

Once the project has been successfully compile and packaged, the utility can be
run from the command line, supplying an optional parameter...

```shell
$ quotely [English|Russian]
```

When there is no language parameter specified, the utility will default to English.

# TO-DOs and Notes
The exercise took the maximum amount of time allotted as there were some issues
with environment and git management. With that in mind, I would have done the following
had I invested more time...

* Add a more specific exception type to the Quotely service so as not to depend
too heavily on RuntimeExceptions
* Added more thorough response checks from the API output

While my intent was to take a more TDD approach, the method I used was more of
a hybrid given the time constraints. I started with a fairly large `fetchQuote`
method, then decomposed it into smaller methods to provide better testability.
