Strategy 1: **Manual exclusion during merge**


     1 │ # When merging dev → master, exclude db-loader
     2 │ git checkout master
     3 │ git merge dev --no-commit
     4 │ git reset HEAD db-loader/  # Unstage db-loader
     5 │ git commit -m "Merge dev changes (excluding db-loader)"


Strategy 2: **Separate merge commits**


     1 │ # Create production branch without db-loader
     2 │ git checkout dev
     3 │ git checkout -b dev-for-prod
     4 │ git rm -r db-loader/
     5 │ git commit -m "Remove db-loader for production"
     6 │ git checkout master
     7 │ git merge dev-for-prod
     8 │ git branch -d dev-for-prod


Strategy 3: **Cherry-pick specific files/commits**


     1 │ git checkout master
     2 │ # Only pick commits that don't touch db-loader
     3 │ git cherry-pick abc123 def456