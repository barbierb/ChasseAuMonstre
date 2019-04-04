sudo apt-get install git

git clone https://git-iut.univ-lille1.fr/ChasseAuMonstre/chasseaumonstre.git

cd chasseaumonstre
ECRIRE SES FICHIERS
git add FICHIERS
git commit -m "nom du commit"
git push -u origin master

```bash
METTRE A JOUR TOUT LE MONDE DEPUIS MASTER:
for BRANCH in `ls .git/refs/heads`; do
    git rebase master $BRANCH;
    git checkout $BRANCH;
    git push;
done
```