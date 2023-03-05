### Voici notre super application mobile (Android) !

## 1. Nous avons mis en place une **frame d'acceuil**, elle comporte:
+ une **liste déroulante** contenant l'ip, le nom et la couleur utilisés. C'est un **historique**. Cela permet de récupérer ses presets et de ne pas retaper l'ip et son nom. Toutes ses informations sont stockées dans un File spécifique à l'application.
+ Une **bannière** dans laquelle il y'a un **bouton d'aide** qui ouvre une frame pop-up (pas de nouvelle activité pour ceci). Il y'a un bouton **settings** aussi pour accéder aux paramètres de l'application, on peut changer sur cette activité la couleur de son pion.
+ 2 inputText, un pour l'ip du serveur et un pour le nom (**rempli automatiquement** quand on choisi un preset dans l'historique)
+ 2 boutons pour choisir soit le premier controlleur soit le 2ème.
+ un bouton pour se **connecter** au serveur
    

## 2. Nous avons donc mis en place 2 **controlleurs** :
+ 1 avec un **joystick** et des **fonctionnalités de _triches_**
+ 1 avec des **boutons** (en forme de croix) sans les **fonctionnalités de _triches_**


## 3. Concernant le **controlleur avec boutons** (sans triche):
+ Mise en place des boutons Avancer, tourner à droite, tourner à gauche, le bouton reculer ne fonctionne pas car il est inutile pour moi
+ Mise en place du bouton tirer, il ne fait que tirer devant le joueur.
+ Essai de mise en place de **changement de direction en fonction de l'orientation** du pion (_cf. orientation.png_).
> Pour le changement de direction en fonction de l'orientation plusieurs option s'offrait à moi :
> J'ai d'abord commencé par changer la **place** des boutons en fonction de l'orientation du joueur. Mais cette solution demande trop de ressource et n'est pas top niveau IHM
- J'en ai donc conclus qu'il fallait que je change le **contenu des sendMessage()** en fonction des orientations.
- Mais un autre problème est survenu ! Comment récupérer les orientations ? J'ai mis en place un **pattern producteur** (producteur de'orientation, thread qui tourne en boucle qui récupère les orientations du pion) **consommateur** (threads du controlleur qui récupère les dernières orientations enregistrées).
    
