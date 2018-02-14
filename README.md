# 1. Rete neurale

### 1.1 Neurone
_classe Neuron.java_
il neurone è l'unità minima della rete neurale. La sua struttura comprende:
* alcuni dendriti che trasportano nel neurone dei valori di input
* un corpo che processa l'informazione
* un assone che trasporta fuori dal neurone il valore di output

Ciascun neurone è caratterizzato da alcuni elementi che determinano il suo funzionamento:
* un peso per ogni dendrite
* una soglia di attivazione
* una funzione di attivazione

Tutti i valori (input, pesi, soglia di attivazione, output) sono compresi tra 0 ed 1memorizzati in variabili float.

__1.1.1 funzionamento__

Il neurone riceve i valori in input a ciascun dendrite attraverso un array (di float) passato come parametro al metodo _activate()_.

Ciascun valore viene poi moltiplicato per il rispettivo peso (salvati in _pesi_).

Questi valori vengono quindi sommati e divisi per il loro numero (viene fatta la media) in modo da ottenere un valore compreso tra 0 ed 1.

Il valore così ottenuto viene passato alla _funzioneDiAttivazione()_ che restituisce il risultato da restituire come output del neurone.

__1.1.2 funzione di attivazione__

La funzione di attivazione che ho scelto è la seguente:

![F_attivazione](/images/f_att.gif)

* y - risultato in output
* α - parametro che influenza la derivata prima della funzione (fissato da codice)
* x - valore fornito in input alla funzione
* γ - soglia di attivazione

La funzione ha il seguente grafico: (α = 50, γ = 0.5)

![F_attivazione](/images/graph_f_att.png)
