__INSTALLATION, PREMIER COMMIT et PUSH__
```
sudo apt-get install git
git clone https://git-iut.univ-lille1.fr/ChasseAuMonstre/chasseaumonstre.git
cd chasseaumonstre
ECRIRE SES FICHIERS
git add FICHIERS
git commit -m "nom du commit"
git push -u origin master
```

__METTRE A JOUR TOUT LE MONDE DEPUIS MASTER__
```bash
for BRANCH in `ls .git/refs/heads`; do
    git rebase master $BRANCH;
    git checkout $BRANCH;
    git push;
done
```


__MODIFS MANUELLES D'UNE BRANCHE A L'AUTRE VIA ECLIPSE EGIT__
```
switch sur la branche à récupérer localement
synchronize workspace
changer le mode de sync
merge all
et modif les gros changements manuellement
```