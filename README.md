# Public repository

My favorite way of running `.java` files is using this function

    function jcar { javac ${1%.*}.java && java ${1%.*} }
